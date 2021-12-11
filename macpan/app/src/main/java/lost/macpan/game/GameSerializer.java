package lost.macpan.game;

import com.google.gson.*;

import java.io.Serializable;
import java.lang.reflect.Type;

public class GameSerializer implements JsonSerializer<Game>, JsonDeserializer<Game> {

    @Override
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
                jsonGame.get("levelNr").getAsInt(),
                savedFlags);
    }

    @Override
    public JsonElement serialize(Game game, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonGame = new JsonObject();

        //Gson jsonMap = new Gson();
        //String saveMap = jsonMap.toJson(game.getMap());


        //Gson jsonMap = new GsonBuilder().setPrettyPrinting().create();
        //String saveMap = jsonMap.toJson(game.getMap());

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
        jsonGame.addProperty("levelNr", game.getLevelNr());
        jsonGame.add("Flags", jsonFlags);


        return jsonGame;
    }

}
