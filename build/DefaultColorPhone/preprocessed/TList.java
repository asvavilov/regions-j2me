import javax.microedition.lcdui.*;

public class TList extends VisualComponent {
  //private String text;
  String strings[];
  int rows;
  int start;
  int scroll_width=3;
  public TList(int x, int y, int w, int h) {
    super(x,y,w,h);
    start=0;
    rows=this.height/app.FontHeight[app.fontInd];
  }
  public void setText(String s) {
    //this.text=s;
    this.strings=app.prepareString(s);
  }
  public boolean down() {
    if (this.strings.length-this.start<=this.rows) return false;
    this.start++;
    return true;
  }
  public boolean up() {
    if (this.start-1<0) return false;
    this.start--;
    return true;
  }
  public boolean left() {
    if (this.start==0) return false;
    if (this.start-this.rows<0) {
      this.start = 0;
    } else {
      this.start -= this.rows;
    }
    return true;
  }
  public boolean right() {
    if (this.start>=this.strings.length-this.rows) return false;
    this.start+=this.rows;
    if (this.start+this.rows>this.strings.length) this.start=this.strings.length-this.rows;
    return true;
  }
  private void drawScroll() {
    app.gr.setColor(0xaaaaaa);
    app.gr.fillRect(app.width-this.scroll_width, this.posY, this.scroll_width, this.height);
    app.gr.setColor(0x555555);
    app.gr.fillRect(app.width-this.scroll_width, 1 + this.posY + (int)(this.start * this.height / this.strings.length), this.scroll_width, (int)(this.rows * this.height / this.strings.length));
  }
  public void repaint() {
    app.gr.setColor(0xffffaa);
    app.gr.fillRect(this.posX,this.posY,this.width,this.height);
    drawScroll();//до или после drawString?
    app.drawString(this.strings, this.posX, this.posY, this.start, this.rows);
  }
}
