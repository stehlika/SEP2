package Domain.Model;

import java.util.ArrayList;

/**
 * Created by Karolina on 22/05/2017.
 */
public class HouseList {
    private ArrayList<House> houses;

    public HouseList()
    {
        this.houses = new ArrayList<>(4);
    }

    public ArrayList<House> getHouses() {
        return houses;
    }

    public void setHouses(ArrayList<House> houses) {
        this.houses = houses;
    }

    public void addHouse(House house)
    {
        if (!houses.contains(house.getFaculty()))
            houses.add(house);
    }

    public int getNumberOfHouses()
    {
        return houses.size();
    }

    public House getHouse(int index)
    {
        return houses.get(index);
    }

    @Override
    public String toString() {
        return "HouseList{" +
                "houses=" + houses +
                '}';
    }
}
