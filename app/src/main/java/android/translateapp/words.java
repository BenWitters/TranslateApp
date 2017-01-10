package android.translateapp;



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