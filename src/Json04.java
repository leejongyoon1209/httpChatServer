
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Json04 {
    static void main(String[] args) {

        JSONObject superHeroes = new JSONObject();

        superHeroes.put("squadName", "Super hero squad");
        superHeroes.put("homeTown", "Metro City");
        superHeroes.put("formed", 2016);
        superHeroes.put("secretBase", "Super tower");
        superHeroes.put("active", true);

        JSONArray members = new JSONArray();

        JSONObject member1 = new JSONObject();
        member1.put("name", "Molecule Man");
        member1.put("age", 29);
        member1.put("secretIdentity", "Dan Jukes");

        JSONArray Powers1 = new JSONArray();
        Powers1.put("Radiation resistance");
        Powers1.put("Turning tiny");
        Powers1.put("Radiation blast");
        member1.put("powers", Powers1);

        members.put(member1);

        JSONObject member2 = new JSONObject();
        member2.put("name", "Madame Uppercut");
        member2.put("age", 39);
        member2.put("secretIdentity", "Jane Wilson");

        JSONArray Power2 = new JSONArray();
        Power2.put("Million tonne punch");
        Power2.put("Damage resistance");
        Power2.put("Superhuman reflexes");
        member2.put("powers", Power2);

        members.put(member2);

        JSONObject member3 = new JSONObject();
        member3.put("name", "Eternal Flame");
        member3.put("age", 1000000);
        member3.put("secretIdentity", "Unknown");

        JSONArray Power3 = new JSONArray();
        Power3.put("Immortality");
        Power3.put("Heat Immunity");
        Power3.put("Inferno");
        Power3.put("Teleportation");
        Power3.put("Interdimensional travel");
        member3.put("powers", Power3);

        members.put(member3);

        superHeroes.put("members", members);

        System.out.println(superHeroes.getString("homeTown"));
        System.out.println(superHeroes.getBoolean("active"));
        System.out.println(superHeroes.getJSONArray("members").getJSONObject(1)
                .getJSONArray("powers").getString(2));

        try (FileWriter fileWriter = new FileWriter("lec-06-prg-04-json-example.json")) {
            fileWriter.write(superHeroes.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}