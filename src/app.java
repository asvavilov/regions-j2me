import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class app extends MIDlet
{
    static strint RegNum;
    public static int width;
    public static int height;
    static app instance;
    static frmMain displayable;
    static frmInfo displayable2;
    static Display disp;
    static Graphics gr;
    static Image buffer;
    static int bottomPanelHeight;
    static int curX;
    static int curY;
    static int dx;
    static int dy;
    static char gc;
    static int charposX, charposY;
    static final String FontArray = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_°abcdefghijklmnopqrstuvwxyz{|}~|АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя";
    static int FontWidth[] = {6,8};
    static int FontHeight[] = {9,12};
    static int fontInd;
    static Image fontImage[];
    static data region;
    static Alert alert_help;
    static Command cmd_help;
    static Command cmd_exit;

    public app()
    {
        displayable = new frmMain();
        displayable2 = new frmInfo();
        instance = this;
        buffer = Image.createImage(app.width, app.height);
        gr = buffer.getGraphics();
        disp=Display.getDisplay(this);
        disp.setCurrent(displayable);
        RegNum=new strint();
        region=new data();
        gc = 0xff;
        fontImage=new Image[2];
        try {
          fontImage[0] = Image.createImage(getClass().getResourceAsStream("/font1.png"));
          fontImage[1] = Image.createImage(getClass().getResourceAsStream("/font2.png"));
        }
        catch (Exception e) {
          e.printStackTrace();
        }
    }

    public void startApp()
    {
    }

    public void pauseApp()
    {
    }

    public void destroyApp(boolean flag)
    {
    }

    public static void quitApp()
    {
        instance.destroyApp(true);
        instance.notifyDestroyed();
        instance = null;
    }
    public static void changeForm(Displayable frm) {
      disp.setCurrent(frm);
    }

  private static String[] explode(String s, char delimiter)
  {
    String t=s;
    int i=1,x=0;
    while((x = t.indexOf(delimiter,++x))>=0) i++;
    String result[] = new String[i];
    i=x=0;
    while((x = t.indexOf(delimiter))>=0) {
      result[i]=t.substring(0,x);
      t=t.substring(x+1);
      i++;
    }
    result[i]=t;
    return result;
  }

  private static String[] explode2(String s) {
    String t="";
    int pos=0,old_pos=0,rel_pos=0,cur_len=0;
    int cols=(width)/FontWidth[app.fontInd];
    //while ((pos=s.indexOf(' ',pos))>=0) {
    for (pos = 0; pos < s.length(); pos++) {
      if (s.charAt(pos) == ' ' || s.charAt(pos) == '-') {
        pos++;
        cur_len = pos - old_pos;
        if (rel_pos + cur_len > cols) {
          rel_pos = 0;
          t += "\n";
        }
        rel_pos += cur_len;
        t += s.substring(old_pos, pos);
        old_pos = pos;
      }
    }
    cur_len=s.length()-old_pos;
    if (rel_pos+cur_len>cols) t+="\n";
    t+=s.substring(old_pos);
    String result[]=explode(t,'\n');
    return result;
  }

  private static void drawChar(char c)
  {
    if (c=='\r') {
      return ;
    }
    if (c == '\n')
    {
        curX = 1;
        curY += FontHeight[app.fontInd];
    } else {
      if (c==0x00AB || c==0x00BB) {
        //фигурные кавычки = обычные кавычки
        c=0x0022;
      } else if (c==0x2013 || c==0x2014) {
        //короткое или длинное тире = обычное тире
        c=0x002D;
      }
      int n=FontArray.indexOf(c);
      if (n==-1) {
        //метод Canvas, на крайний случай, если символа нет в fontImage
        app.gr.setColor(0, 0, 0);
        app.gr.drawChar(c, curX, curY-FontHeight[app.fontInd]/2, 0);
        //!!!хотя можно ничего не выводить
      } else {
        gc = c;
        dx = FontWidth[app.fontInd];
        dy = FontHeight[app.fontInd];
        charposY = n / 16;
        charposX = n - charposY * 16;
        app.gr.drawRegion(fontImage[app.fontInd], charposX * FontWidth[app.fontInd],
        charposY * FontHeight[app.fontInd], dx, dy, 0, curX, curY, 20);
      }
      curX += FontWidth[app.fontInd];
    }
  }

  public static String[] prepareString(String s) {
    int k=0;
    String st[] = explode(s,'\n');
    String st2[][]=new String[st.length][];
    int lines=0;
    for(int i = 0; i < st.length; i++) {
      st2[i] = explode2(st[i]);
      lines+=st2[i].length;
    }
    String result[]=new String[lines];
    for (int i=0;i<st.length;i++) {
      for (int j=0;j<st2[i].length;j++) {
        result[k++] = st2[i][j];
      }
    }
    return result;
  }

  public static void drawString(String s[], int xxx, int yyy) {
    drawString(s,xxx,yyy,0,0);
  }

  public static void drawString(String s[], int xxx, int yyy, int start, int rows)
  {
    curX = xxx;
    curY = yyy;
    int lines=s.length;
    if (start+rows>lines) rows=lines-start;
    if (rows==0) rows=lines;
    for (int i=0;i<s.length;i++) {
      if (i>=start && i<start+rows) {
        for (int j = 0; j < s[i].length(); j++) {
          drawChar(s[i].charAt(j));
        }
        drawChar('\n');
      }
    }
  }
}
