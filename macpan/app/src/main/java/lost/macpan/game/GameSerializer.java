package lost.macpan.game;

import com.google.gson.*;

import java.io.Serializable;
import java.lang.reflect.Type;

public class GameSerializer implements JsonSerializer<Game>, JsonDeserializer<Game> {

    @Override
    public Game deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonGame = jsonElement.getAsJsonObject();

       // Gson getArrayMap = new GsonBuilder().create();
       // char[][] savedMap = getArrayMap.fromJson(jsonGame.getAsString("Map"), char[][].class);

        Gson getFlags = new Gson();
        boolean[] savedFlags = getFlags.fromJson(jsonGame.getAsJsonArray("Flags"), boolean[].class);

        return new Game (
                //savedMap,
                jsonGame.get("Score").getAsInt(),
                jsonGame.get("TimerSpeed").getAsInt(),
                jsonGame.get("TimerDeathTouch").getAsInt(),
                jsonGame.get("TimerCoinBoost").getAsInt(),
                jsonGame.get("TimerFreeze").getAsInt(),
                savedFlags);
    }

    @Override
    public JsonElement serialize(Game game, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonGame = new JsonObject();

        Gson jsonMap = new Gson();
        String saveMap = jsonMap.toJson(game.getMap());


       /* Gson jsonMap = new GsonBuilder().setPrettyPrinting().create();
        String saveMap = jsonMap.toJson(game.getMap());*/

        JsonArray jsonFlags = new JsonArray();
        for(boolean element : game.getFlags())
            jsonFlags.add(element);


        jsonGame.addProperty("Map", saveMap);
        jsonGame.addProperty("Score", game.getScore());
        jsonGame.addProperty("TimerSpeed", game.getTimerSpeed());
        jsonGame.addProperty("TimerFreeze", game.getTimerFreeze());
        jsonGame.addProperty("TimerDeathTouch", game.getTimerDeathTouch());
        jsonGame.addProperty("TimerCoinBoost", game.getTimerCoinBoost());
        jsonGame.add("Flags", jsonFlags);


        return jsonGame;
    }

}
