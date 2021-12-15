package lost.macpan.utils;

import com.google.gson.*;
import lost.macpan.game.Game;

import java.lang.reflect.Type;

public class GameSerializer implements JsonSerializer<Game>, JsonDeserializer<Game> {

    public Game deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonGame = jsonElement.getAsJsonObject();

        Gson getArrayMap = new GsonBuilder().create();
        char[][] savedMap = getArrayMap.fromJson(jsonGame.get("Map"), char[][].class);

        Gson getFlags = new Gson();
        boolean[] savedFlags = getFlags.fromJson(jsonGame.getAsJsonArray("Flags"), boolean[].class);

        return new Game (
                savedMap,
                jsonGame.get("Score").getAsInt(),
                jsonGame.get("TimerSpeed").getAsInt(),
                jsonGame.get("TimerDeathTouch").getAsInt(),
                jsonGame.get("TimerCoinBoost").getAsInt(),
                jsonGame.get("TimerFreeze").getAsInt(),
                jsonGame.get("LevelNr").getAsInt(),
                savedFlags);
    }

    public JsonElement serialize(Game game, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonGame = new JsonObject();

        JsonArray mapArray = new JsonArray();
        for (int i=0; i<game.getMaxColumns(); i++){
            JsonArray rowsArray  = new JsonArray();
            for (int j=0; j<game.getMaxRows(); j++){
                rowsArray.add(game.getMap()[i][j]);
            }
            mapArray.add(rowsArray);
        }

        JsonArray jsonFlags = new JsonArray();
        for(boolean element : game.getFlags())
            jsonFlags.add(element);


        jsonGame.add("Map", mapArray);
        jsonGame.addProperty("Score", game.getScore());
        jsonGame.addProperty("TimerSpeed", game.getTimerSpeed());
        jsonGame.addProperty("TimerFreeze", game.getTimerFreeze());
        jsonGame.addProperty("TimerDeathTouch", game.getTimerDeathTouch());
        jsonGame.addProperty("TimerCoinBoost", game.getTimerCoinBoost());
        jsonGame.add("Flags", jsonFlags);
        jsonGame.addProperty("LevelNr", game.getLevelNr());


        return jsonGame;
    }
}
