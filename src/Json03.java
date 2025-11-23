import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
public class Json03 {
    static void main(String[] args) throws Exception {

        String fileName = Files.readString(Paths.get("lec-06-prg-03-json-example.json"));
        JSONObject superHeroes = new JSONObject(fileName);

        System.out.println(superHeroes.getString("homeTown"));
        System.out.println(superHeroes.getBoolean("active"));
        System.out.println(superHeroes.getJSONArray("members").getJSONObject(1).getJSONArray("powers").getString(2));
    }
}
