package se233.chapter1.model.character;

import java.lang.*;
import se233.chapter1.model.item.Weapon;
import se233.chapter1.model.item.Armor;

public class BasedCharacter {
    protected String name, imgpath;
    protected DamageType type;
    protected Integer fullHp, basedPow, basedDef, basedRes;
    protected Integer hp, power, defense, resistance;
    protected Weapon weapon;
    protected Armor armor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagepath() {
        return imgpath;
    }

    public void setImagepath(String imgpath) {
        this.imgpath = imgpath;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getFullHp() {
        return fullHp;
    }

    public void setFullHp(Integer fullHp) {
        this.fullHp = fullHp;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getResistance() {
        return resistance;
    }

    public void setResistance(Integer resistance) {
        this.resistance = resistance;
    }

    public DamageType getType() {
        return type;
    }

    public void setType(DamageType type) {
        this.type = type;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void equipWeapon(Weapon weapon) {
        if(weapon != null){
            this.power = this.basedPow + weapon.getPower();
        }else{
            this.weapon = weapon;
        }
    }

    public void equipArmor(Armor armor) {
        if(armor != null){
            this.defense = this.basedDef + armor.getDefense();
            this.resistance = this.basedRes + armor.getResistance();
        }else{
            this.armor = armor;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}