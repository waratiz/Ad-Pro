package se233.chapter1.controller;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import se233.chapter1.Launcher;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

public class AllCustomHandler {

    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
                ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
                BasedCharacter character = Launcher.getMainCharacter();
                boolean hasUnequipped = false;

                if(Launcher.getEquippedWeapon() != null){
                    allEquipments.add(Launcher.getEquippedWeapon());
                    Launcher.setEquippedWeapon(null);
                    character.equipWeapon(null);
                    hasUnequipped = true;
                }
                if (Launcher.getEquippedArmor() != null) {
                    allEquipments.add(Launcher.getEquippedArmor());
                    Launcher.setEquippedArmor(null);
                    character.equipArmor(null);
                    hasUnequipped = true;
                }
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.refreshPane();
        }
    }
    public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imgView) {
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());

        ClipboardContent content = new ClipboardContent();
        content.put(BasedEquipment.DATA_FORMAT, equipment);
        db.setContent(content);

        event.consume();
    }

    public static void onDragOver(DragEvent event, String type) {
        Dragboard dragboard = event.getDragboard();
        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);

        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT)
                && retrievedEquipment.getClass().getSimpleName().equals(type)) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
    }

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;

        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();

        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
            BasedCharacter character = Launcher.getMainCharacter();
            boolean isBattlemage = character instanceof se233.chapter1.model.character.BattleMageCharacter;
            boolean isMage = character instanceof se233.chapter1.model.character.MagicalCharacter;
            boolean isKnight = character instanceof se233.chapter1.model.character.PhysicalCharacter;

            if (retrievedEquipment.getClass().getSimpleName().equals("Weapon")) {
                Weapon weapon = (Weapon) retrievedEquipment;

                // Battlemage can equip weapons of any DamageType; other classes must match
                if (!isBattlemage) {
                    if (weapon.getDamageType() != character.getType()) {
                        System.out.println("Equip Failed: DamageType doesn't match character type!");
                        event.setDropCompleted(false);
                        return;
                    }
                }

                if (Launcher.getEquippedWeapon() != null)
                    allEquipments.add(Launcher.getEquippedWeapon());
                Launcher.setEquippedWeapon(weapon);
                character.equipWeapon(weapon);
            } else {

                if (isBattlemage) {
                    System.out.println("Equip Failed: Battlemage cannot equip armor!");
                    event.setDropCompleted(false);
                    return;
                }

                if (Launcher.getEquippedArmor() != null)
                    allEquipments.add(Launcher.getEquippedArmor());
                Launcher.setEquippedArmor((Armor) retrievedEquipment);
                character.equipArmor((Armor) retrievedEquipment);
            }
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();
            ImageView imgView = new ImageView();

            if (imgGroup.getChildren().size() != 1) {
                imgGroup.getChildren().remove(1);
                Launcher.refreshPane();
            }

            lbl.setText(retrievedEquipment.getClass().getSimpleName() + ":\n" + retrievedEquipment.getName());
            imgView.setImage(new Image(Launcher.class.getResource(retrievedEquipment.getImagepath()).toString()));
            imgGroup.getChildren().add(imgView);

            dragCompleted = true;
        }

        event.setDropCompleted(dragCompleted);
    }

    public static void onEquipDone(DragEvent event) {
        if (event.getTransferMode() == TransferMode.MOVE) {
            Dragboard dragboard = event.getDragboard();
            ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
            BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
            int pos = -1;

            for (int i = 0; i < allEquipments.size(); i++) {
                if (allEquipments.get(i).getName().equals(retrievedEquipment.getName())) {
                    pos = i;
                    break;
                }
            }

            if (pos != -1) {
                allEquipments.remove(pos);
            }

            Launcher.setAllEquipments(allEquipments);
        }
        Launcher.refreshPane();
    }

}