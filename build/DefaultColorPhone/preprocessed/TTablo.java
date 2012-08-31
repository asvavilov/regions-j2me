import javax.microedition.lcdui.*;

public class TTablo extends VisualComponent {
  static Image numbers;
  static Image background;
  public TTablo(int x, int y) {
    super(x,y);
    try{
      numbers = Image.createImage(getClass().getResourceAsStream("/numbers.png"));
      background = Image.createImage(getClass().getResourceAsStream("/tablo.png"));
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  public void repaint() {
  app.gr.drawImage(background, posX, posY, Graphics.BOTTOM | Graphics.HCENTER);
  for (int i=app.RegNum.maxlength-app.RegNum.length();i<app.RegNum.maxlength;i++) {
    //app.gr.drawRegion(numbers, app.RegNum.getInt(i) * 20, 0, 20, 32, 0, posX + (i) * 20 - 10, posY, Graphics.BOTTOM | Graphics.HCENTER);
    app.gr.drawRegion(numbers, app.RegNum.getInt(i) * 20, 0, 20, 32, 0, posX + (i) * 20 - 20, posY-background.getHeight(), Graphics.TOP | Graphics.LEFT);
    }
  }
}
