package rireport.ridev.com.br.Libs;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("all")
public class JSONMessage {

  private JSONObject chatObject;

  public JSONMessage(String text) {
    chatObject = new JSONObject();
    chatObject.put("text", FancyText.colored(text));
  }

  public JSONMessage addExtra(ChatExtra extraObject) {
    if (!chatObject.containsKey("extra")) {
      chatObject.put("extra", new JSONArray());
    }
    
    JSONArray extra = (JSONArray) chatObject.get("extra");
    extra.add(extraObject.toJSON());
    chatObject.put("extra", extra);
    return this;
  }

  public String toString() {
    return chatObject.toJSONString();
  }

  public static class ChatExtra {
    private JSONObject chatExtra;

    public ChatExtra(String text) {
      chatExtra = new JSONObject();
      chatExtra.put("text", FancyText.colored(text));
    }

    public ChatExtra addClickEvent(ClickEventType action, String value) {
      JSONObject clickEvent = new JSONObject();
      clickEvent.put("action", action.getTypeString());
      clickEvent.put("value", FancyText.colored(value));
      chatExtra.put("clickEvent", clickEvent);
      return this;
    }

    public ChatExtra addHoverEvent(HoverEventType action, String value) {
      JSONObject hoverEvent = new JSONObject();
      hoverEvent.put("action", action.getTypeString());
      hoverEvent.put("value", FancyText.colored(value));
      chatExtra.put("hoverEvent", hoverEvent);
      return this;
    }

    public JSONObject toJSON() {
      return chatExtra;
    }

    public ChatExtra build() {
      return this;
    }
  }

  public static enum ClickEventType {
    RUN_COMMAND("run_command"), SUGGEST_COMMAND("suggest_command"), OPEN_URL(
        "open_url"), CHANGE_PAGE("change_page");

    private final String type;

    ClickEventType(String type) {
      this.type = type;
    }

    public String getTypeString() {
      return type;
    }
  }

  public static enum HoverEventType {
    SHOW_TEXT("show_text"), SHOW_ITEM("show_item"), SHOW_ACHIEVEMENT("show_achievement");
    private final String type;

    HoverEventType(String type) {
      this.type = type;
    }

    public String getTypeString() {
      return type;
    }
  }
}
