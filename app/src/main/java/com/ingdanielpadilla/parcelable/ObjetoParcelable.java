package com.ingdanielpadilla.parcelable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Lucia on 16/11/2015.
 */
public class ObjetoParcelable implements Parcelable {
    private String name,price,image,id,description;
    private Bitmap pic;
    private int quantity=0,max=0;
    private final static int numprop=8;

    public ObjetoParcelable(String nombre,String precio,String imagen,String id,String descripcion){
        this.name=nombre;
        this.price=precio;
        this.image=imagen;
        this.id=id;
        this.description=descripcion;

    }

    public String getName(){
        return this.name;
    }
    public String getPrice(){
        return this.price;
    }
    public String getImage(){
        return this.image;
    }
    public Bitmap getPic(){return this.pic;}
    public String getId(){return this.id;}
    public String getDescription(){return this.description;}
    public Integer getQuantity(){return this.quantity;}
    public void setPic(Bitmap pic){this.pic=pic;}
    public void setQuantity(Integer cantidad){this.quantity=cantidad;}
    public void setMax(Integer cantidad){this.max=cantidad;}
    public void plusQuantity(int cantidad){
        if((this.quantity + cantidad)<=max) {
            this.quantity = (this.quantity + cantidad);
        }
    }

    public void lessQuantity(int cantidad) {
        if (this.quantity > 0) {
            this.quantity = (this.quantity - cantidad);
        }
    }

    public ObjetoParcelable(Parcel in){
        String[] data= new String[numprop];

        in.readStringArray(data);
        this.name= data[0];
        this.price= data[1];
        this.image= data[2];
        this.id=data[3];
        this.description=data[4];
        byte [] encodeByte= Base64.decode(data[5], Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        this.pic=bitmap;
        this.quantity=Integer.parseInt(data[6]);
        this.max=Integer.parseInt(data[7]);
    }
    public String[] toStringArray(){
        String[] data= new String[numprop];
        data[0]=this.name;
        data[1]=this.price;
        data[2]=this.image;
        data[3]=this.id;
        data[4]=this.description;
        data[5]="";
        data[6]=Integer.toString(this.quantity);
        data[7]=Integer.toString(this.max);

        return data;
    }
    @Override
    public int describeContents() {
// TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
// TODO Auto-generated method stub
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        this.pic.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        dest.writeStringArray(new String[]{this.name,this.price,this.image,this.id,this.description,temp,String.valueOf(this.quantity),String.valueOf(this.max)});
    }

    public static final Parcelable.Creator<ObjetoParcelable> CREATOR= new Parcelable.Creator<ObjetoParcelable>() {

        @Override
        public ObjetoParcelable createFromParcel(Parcel source) {
// TODO Auto-generated method stub
            return new ObjetoParcelable(source);  //using parcelable constructor
        }

        @Override
        public ObjetoParcelable[] newArray(int size) {
// TODO Auto-generated method stub
            return new ObjetoParcelable[size];
        }
    };
}
