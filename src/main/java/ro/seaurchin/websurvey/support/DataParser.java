package ro.seaurchin.websurvey.support;

/**
 * User: BogdanCo
 * Date: Aug 28, 2006
 * Time: 5:04:57 PM
 */
class DataParser
   {
       String key;
       Long idChestionar;
       Long idIntrebare;
       Long idOptiune;
       int isIntrebareRadioWhithoutClearText;


       public DataParser(String key, Object o)
       {
           this.key=key;
           String [] stuff=key.split("_");
           for(int i = 0; i<stuff.length; i++)
           {
               if(stuff[i].toCharArray()[0]=='C')
               {
                   isChestionarSetId(stuff[i]);
               }

               if(stuff[i].toCharArray()[0]=='I')
               {
                   if(stuff[i].toCharArray()[1]=='R'){
                       if(stuff[i].toCharArray()[2]=='E')
                       {
                           isIntrebareExtraTextSetId(stuff[i]);
                           this.setIdOptiune(new Long((Long.parseLong(stuff[i].substring(4)))));
                           isIntrebareRadioWhithoutClearText=0;
                       }
                       else
                       {
                           isIntrebareRadioSetId(stuff[i]);
                           isIntrebareRadioSetIdOptiuneFromData(stuff[i],o);
                           isIntrebareRadioWhithoutClearText=1;
                       }
                   }
                   else
                   {
                       isIntrebareSetId(stuff[i]);
                   }
               }

               if(stuff[i].toCharArray()[0]=='O')
               {
                   isOptiuneSetId(stuff[i]);
               }

           }
       }

       private void isIntrebareRadioSetId(String s) {
           String idIntrebareString = s.substring(2,s.length());
           this.idIntrebare=new Long(Long.parseLong(idIntrebareString));
       }

       private void isIntrebareRadioSetIdOptiuneFromData(String s, Object o) {
           String data = ((String[])o)[0];
           DataParser dp=new DataParser(data, new Object());
           this.setIdOptiune(dp.getIdOptiune());
       }

       private void isIntrebareExtraTextSetId(String s)
       {
           String idIntrebareString = s.substring(4,s.length());
           this.idIntrebare=new Long(Long.parseLong(idIntrebareString));
       }

       private void isOptiuneSetId(String s)
       {
           String idOptiuneString = s.substring(1,s.length());
           this.idOptiune=new Long(Long.parseLong(idOptiuneString));
       }

       private void isIntrebareSetId(String s) {
           String idIntrebareString = s.substring(1,s.length());
           this.idIntrebare=new Long(Long.parseLong(idIntrebareString));
       }

       private void isChestionarSetId(String s)
       {
           String idChestionarString = s.substring(1,s.length());
           this.idChestionar=new Long(Long.parseLong(idChestionarString));
       }

       public Long getIdChestionar() {
           return idChestionar;
       }

       public void setIdChestionar(Long idChestionar) {
           this.idChestionar = idChestionar;
       }

       public Long getIdIntrebare() {
           return idIntrebare;
       }

       public void setIdIntrebare(Long idIntrebare) {
           this.idIntrebare = idIntrebare;
       }

       public Long getIdOptiune() {
           return idOptiune;
       }

       public void setIdOptiune(Long idOptiune) {
           this.idOptiune = idOptiune;
       }

       public int getIntrebareRadioWhithoutClearText() {
           return isIntrebareRadioWhithoutClearText;
       }

       public void setIntrebareRadioWhithoutClearText(int intrebareRadioWhithoutClearText) {
           isIntrebareRadioWhithoutClearText = intrebareRadioWhithoutClearText;
       }

   }

