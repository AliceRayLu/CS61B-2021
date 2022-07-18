package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    //public static final double CONCERT_A = 440.0;
    //public static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        GuitarString[] strings = new GuitarString[37];
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

        for(int i = 0;i < 37;i++){
            strings[i] = new GuitarString(440*Math.pow(2,(i-24)/12));
        }

        while (true) {
            int i = 0;
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                for( ;i < 37;i++){
                    if(key == keyboard.charAt(i)){
                        strings[i].pluck();
                        break;
                    }
                }
            }
            if(i == 37){
                continue;
            }
            /* compute the superposition of samples */
            double sample = 0;
            for(int j = 0;j < 37;j++){
                sample += strings[j].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for(int j = i ;j < 37;j++){
                strings[j].tic();
            }

        }
    }
}
