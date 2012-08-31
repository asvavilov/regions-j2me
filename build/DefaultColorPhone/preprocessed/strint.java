public class strint {
    static final int maxlength=2;//кол-во символов
    private int intVal;//=0;
    private String strVal;//="";
    private static final String cif="0123456789";
    public strint() {
      this.intVal=0;
      this.strVal="";
    }
    private int strtoint(String strV) {
      int res=0;
      for (int L=1,i=this.length()-1;i>=0;i--,L*=10) {
        res+=L*this.cif.indexOf(strV.charAt(i));
      }
      return res;
    }
    private String inttostr(int intV) {
      return String.valueOf(intV);
    }
    protected void clear() {
      this.intVal=0;
      this.strVal="";
    }
    protected void set(String strV) {
      this.strVal=strV;
      this.intVal=this.strtoint(strV);
    }
    protected void set(int intV) {
      this.intVal=intV;
      String strV;
      strV=this.inttostr(intV);
      if (strV.length()<this.maxlength) {
        strV="0"+strV;
      }
      this.strVal=strV;
    }
    protected String getStr() {
      return this.strVal;
    }
    protected int getInt() {
      return this.intVal;
    }
    protected int getInt(int intV) {
      return this.intVal*intV+(1-intV*2)*(this.intVal/10)*(1+9*intV);
    }
    protected void add(String strV) {
      if (this.length()==this.maxlength) this.clear();
      if (this.length()==this.maxlength-1 & this.getInt()==0 & strV.equals("0")) return;
      this.strVal+=strV;
      //if (this.length()>this.maxlength) this.strVal=this.strVal.substring(this.length()-this.maxlength);
      this.set(this.strVal);
    }
    protected void add(int intV) {
      this.add(inttostr(intV));
    }
    protected int length() {
      return this.strVal.length();
    }
  }
