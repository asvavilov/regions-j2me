/*
 попробовать сделать болле универсальным,
 чтобы потом можно было использовать класс TList
 как расширение текущего (TList extends TLabel)
*/

public class TLabel extends VisualComponent {
  String text;
  public TLabel(int x, int y, String text) {
    super(x,y);
    this.text=text;
  }

  public void repaint() {
    String s[]=app.prepareString(this.text);
    app.gr.setColor(0x00ff00);
    app.gr.fillRect(0,this.posY,app.width,s.length*app.FontHeight[app.fontInd]);
    app.drawString(s,this.posX,this.posY);
  }
}
