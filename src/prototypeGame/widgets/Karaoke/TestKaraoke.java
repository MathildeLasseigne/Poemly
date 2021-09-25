package prototypeGame.widgets.Karaoke;

public class TestKaraoke {

    public static void main(String[] args){

        testParse();

    }

    static void testParse(){
        String s = " Hello, my name is Blanc! I'm a student. " +
                "I am new here, so please help me adapt. Can you help me? Thanks." ;

        String[] out = KaraokeColorizer.parseInputToWordsArray(s);
        for(int i = 0; i< out.length; i++){
            System.out.print(out[i]+ ";");
        }

        System.out.println("\nSecond test");

        String[] words = s.split("\\s+");
        for(int i = 0; i< words.length; i++){
            System.out.print(words[i]+ ";");
        }

        System.out.println("\nThird test");

        String[] words2 = s.trim().split("[ ]+");
        for(int i = 0; i< words2.length; i++){
            System.out.print(words2[i]+ ";");
        }
    }
}
