package android.translateapp;

/**
 * Created by yaron on 18/12/16.
 */

class Words {
    public String DutchWord;
    public String FrenchWord;
    public String UserID;
    public Integer Countwords;

    public Words(){
    }

    public Words(String DutchWord, String FrenchWord, String UserID, Integer Countwords){
        this.DutchWord = DutchWord;
        this.FrenchWord = FrenchWord;
        this.UserID = UserID;
        this.Countwords = Countwords;
    }
}