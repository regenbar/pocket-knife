package tester;

import java.lang.String;

public final class R {
  public static final class images {
    public static final String Regenbar = "src\\main\\resources\\images\\regenbar.jpg";

    public static String getPath() {
      return "src/main/resources/images";
    }
  }

  public static final class text {
    public static final String _0_t_text_A = "src\\main\\resources\\text\\0+t text-A.txt";

    public static String getPath() {
      return "src/main/resources/text";
    }

    public static final class text1 {
      public static final String TextB = "src\\main\\resources\\text\\text1\\textB.txt";

      public static String getPath() {
        return "src/main/resources/text/text1";
      }

      public static final class text3 {
        public static final String TextC = "src\\main\\resources\\text\\text1\\text3\\textC.txt";

        public static String getPath() {
          return "src/main/resources/text/text1/text3";
        }
      }
    }

    public static final class text2 {
      public static final String TextD = "src\\main\\resources\\text\\text2\\textD.txt";

      public static String getPath() {
        return "src/main/resources/text/text2";
      }
    }
  }
}
