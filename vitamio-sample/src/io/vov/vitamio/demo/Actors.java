package io.vov.vitamio.demo;

public class Actors {

   /* private String name;
    private String description;
    private String dob;
    private String country;
    private String height;
    private String spouse;
    private String children;*/
    private String Title;
    private String ProName;
    private String Name;
    private String Sort;
    private String Photo; //Act
    private String Classs;
    private String Ip;
    private String Liveid;
    private String Seetime;
    private String Lp;
    private String StartPrice;

    private String ProName2;
    private String Total;
    private String SellName;
    private String Date;
    private String SellPhone;



    private String MerPicture;
    private String Text;

    private String OrdierID;
    private String SellerName;
    private String Pro;
    private String SellerPhone;
    private String OrderDate;
    private String Price;
    private String YesNo;


     public Actors() {
  //  TODO Auto-generated constructor stub
    }

    public Actors (String photo ,String title, String photo1) {
        super();
        /*this.name = name;
        this.description = description;
        this.dob = dob;
        this.country = country;
        this.height = height;
        this.spouse = spouse;
        this.children = children;*/
        this.Photo = photo;
        this.Title=title;
        this.MerPicture=photo1;



    }
    public String getImage() {
        return Photo;
    }

    public void setImage(String photo) {
        this.Photo = photo;
    }

    public String getTitle() {return Title;}

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getName() {return Name;}

    public void setName(String name) {
        this.Name = name;
    }

    public String getProName() {return ProName;}

    public void setProName(String proName) {
        this.ProName = proName;
    }

    public String getSort() {return Sort;}

    public void setSort(String className) {
        this.Sort = className;
    }

    public String getClasss() {return Classs;}

    public void setClasss(String classs) {
        this.Classs = classs;
    }

    public String getIp() {return Ip;}

    public void setIp(String Ip) {
        this.Ip = Ip;
    }

    public String getLp() {return Lp;}

    public void setLp(String Lp) {
        this.Lp = Lp;
    }

    public String getStartPrice() {return StartPrice;}

    public void setStartPrice(String StartPrice) {
        this.StartPrice = StartPrice;
    }



    public String getLiveid() {return Liveid;}

    public void setLiveid(String Liveid) {
        this.Liveid = Liveid;
    }
    public String getSeetime() {return Seetime;}

    public void setSeetime(String Seetime) {
        this.Seetime = Seetime;
    }



    public String getProName2() {return ProName2;}

    public void setProName2(String proName2) {
        this.ProName2 = proName2;
    }

    public String getTotal() {return Total;}

    public void setTotal(String total) {
        this.Total = total;
    }

    public String getSellName() {return SellName;}

    public void setSellName(String sellid) {
        this.SellName = sellid;
    }

    public String getDate() {return Date;}

    public void setDate(String date) {
        this.Date = date;
    }

    public String getSellPhone() {return SellPhone;}

    public void setSellPhone(String sellPhonee) {this.SellPhone = sellPhonee;}


//------------------------------------------------------------------
    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }
//---------------------------------------------------------------------
    public String getOrdierID() {
    return OrdierID;
}

    public void setOrderID(String OrdierID) {
        this.OrdierID = OrdierID;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String SellerName) {
        this.SellerName = SellerName;
    }

    public String getPro() {
        return Pro;
    }

    public void setPro(String Pro ) {
        this.Pro = Pro;
    }

    public String getSellerPhone() {
        return SellerPhone;
    }

    public void setSellerPhone(String SellerPhone   ) {
        this.SellerPhone = SellerPhone;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate ) {
        this.OrderDate = OrderDate;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price ) {
        this.Price = Price;
    }

    public String getYesNo() {
        return YesNo;
    }

    public void setYesNo(String YesNo ) {
        this.YesNo = YesNo;
    }


}
