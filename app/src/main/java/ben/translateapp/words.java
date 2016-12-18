package ben.translateapp;

/**
 * Created by yaron on 18/12/16.
 */

public class Words {
    public String DutchWord;
    public String FrenchWord;
    public String UserID;

    public Words(){
    }

    public Words(String DutchWord, String FrenchWord, String UserID){
        this.DutchWord = DutchWord;
        this.FrenchWord = FrenchWord;
        this.UserID = UserID;
    }
}