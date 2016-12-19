package android.translateapp;

/**
 * Created by yaron on 18/12/16.
 */

 class Words {
    private String DutchWord;
    private String FrenchWord;
    private String UserID;

    public Words(){
    }

    public Words(String DutchWord, String FrenchWord, String UserID){
        this.DutchWord = DutchWord;
        this.FrenchWord = FrenchWord;
        this.UserID = UserID;
    }

    public String getDutchWord() {
        return DutchWord;
    }

    public void setDutchWord(String dutchWord) {
        DutchWord = dutchWord;
    }

    public String getFrenchWord() {
        return FrenchWord;
    }

    public void setFrenchWord(String frenchWord) {
        FrenchWord = frenchWord;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}