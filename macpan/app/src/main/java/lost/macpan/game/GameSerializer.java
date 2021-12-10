package lost.macpan.game;

import com.google.gson.*;

import java.io.Serializable;
import java.lang.reflect.Type;

public class GameSerializer implements JsonSerializer<Game>, JsonDeserializer<Game> {

    @Override
    public Game deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Game game, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonGame = new JsonObject();

        Gson jsonMap = new Gson();
        String saveMap = jsonMap.toJson(game.getMap());

        Gson jsonFlags = new Gson();
        String saveFlags = jsonFlags.toJson(game.getFlags());

        jsonGame.addProperty("Map", saveMap);
        jsonGame.addProperty("Score", game.getScore());
        jsonGame.addProperty("TimerSpeed", game.getTimerSpeed());
        jsonGame.addProperty("TimerFreeze", game.getTimerFreeze());
        jsonGame.addProperty("TimerSpeed", game.getTimerDeathTouch());
        jsonGame.addProperty("TimerSpeed", game.getTimerSpeed());
        jsonGame.addProperty("Flags", saveFlags);

        return jsonGame;
    }

}
