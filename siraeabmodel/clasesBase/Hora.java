/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siraeabmodel.clasesBase;

/**
 *
 * @author coco
 */
public class Hora {
   private int minutos;
   private int horas;
   //formato HH:MM
   public Hora(){
       minutos  =-1;
       horas=-1;
   }
   
   public Hora(String hora){
       setHora(hora);
   }
   
   public Hora setHora(String hora){
       minutos=-1;
       horas=-1;
       String[] dividido=hora.split(":");
       if(dividido.length==2&&dividido[0].length()==2&&dividido[1].length()==2){
               minutos=Integer.parseInt(dividido[1]);
               horas=Integer.parseInt(dividido[0]);
               if(!(horas<=24&&horas>=0&&minutos<=59&&minutos>=0)){
                    minutos=-1;
                    horas=-1;
               }
           }
        
        return this;
   }
   
   public int restar(Hora hora){
       int minutos;
       minutos=this.minutos+this.horas*60-hora.getMinutos()-hora.getHoras()*60;
       return minutos;
   }

    public int getMinutos() {
        return minutos;
    }

    public int getHoras() {
        return horas;
    }
    
   @Override
    public String toString(){
        String hora=(horas>9?horas+"":"0"+horas)+":"+(minutos>9?minutos+"":"0"+minutos);
        return hora;
    }
    
}
