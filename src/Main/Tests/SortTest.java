package Main.Tests;

import junit.framework.TestSuite;
import Main.Models.Data.Alcohol;
import Main.Models.Data.AlcoholContainer;
import Main.Models.Work.Tasks.Sort;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aleksand Smilyanskiy on 12.06.2016.
 * "The more we do, the more we can do." ©
 */
public class SortTest extends TestSuite {
    String[] alcos = new String[]{
            "WINE,CANADA,198267,3.0,COPPER MOON - MALBEC,30.99",
            "WINE,CANADA,305375,4.0,DOMAINE D'OR - DRY,32.99",
            "WINE,CANADA,53017,4.0,SOMMET ROUGE,29.99",
            "WINE,CANADA,215525,4.0,MISSION RIDGE - PREMIUM DRY WHITE,33.99",
            "WINE,USA,168971,3.0,ZINFANDEL - BIG HOUSE CARDINAL ZIN,36.99",
            "WINE,FRANCE,234559,4.0,LE VILLAGEOIS RED - CELLIERS LA SALLE,34.99",
            "WINE,CANADA,492314,16.0,SAWMILL CREEK - MERLOT,119.0",
            "WINE,CANADA,587584,4.0,SOLA,32.99",
            "WINE,CANADA,100925,0.75,GANTON & LARSEN PROSPECT - PINOT BLANC BIRCH CANOE 2011,13.99",
            "SPIRITS,IRELAND,10157,0.75,JAMESON - IRISH,34.99",
            "WINE,ITALY,102764,0.75,PINOT GRIGIO DELLE VENEZIE - RUFFINO LUMINA,15.99",
            "SPIRITS,USA,103747,0.75,MAKER'S MARK - KENTUCKY BOURBON,44.95",
            "SPIRITS,CANADA,1040,0.75,GORDONS - LONDON DRY,24.49",
            "WINE,CANADA,104679,0.75,CALONA - ARTIST SERIES RESERVE PINOT GRIS 2011/13,12.99",
            "WINE,USA,106476,0.75,PINOT NOIR - SIDURI RUSSIAN RIVER 11/12,49.99",
            "SPIRITS,BRAZIL,107029,0.7,CACHACA 61,28.95"
    };

    public void sort(AlcoholContainer container, Sort.Parameter parameter){
        Sort sort;
        sort = new Sort(container, parameter);
        sort.startWork();
        assertTrue(sort.ready());
        assertTrue(sort.sameOutput());

        boolean ordering = true;
        AlcoholContainer after = sort.getDataAfter();
        for (int i = 0; i<container.size() - 1; i++){
            if (!ordering){
                break;
            }
            switch (parameter.getParameterId()){
                case 0:{
                    if (after.getInfo(i).className.compareTo(after.getInfo(i+1).className) == 1){
                        ordering = false;
                    }
                    break;
                }
                case 1:{
                    if (after.getInfo(i).name.compareTo(after.getInfo(i+1).name) == 1){
                        ordering = false;
                    }
                    break;
                }
                case 2:{
                    if (after.getInfo(i).id.compareTo(after.getInfo(i+1).id) == 1){
                        ordering = false;
                    }
                    break;
                }
                case 3:{
                    if (after.getInfo(i).litres > after.getInfo(i+1).litres){
                        ordering = false;
                    }
                    break;
                }
                case 4:{
                    if (after.getInfo(i).price > after.getInfo(i+1).price){
                        ordering = false;
                    }
                    break;
                }
            }

        }
        assertTrue(ordering);

        System.out.println(sort);
        System.out.println(after.dataIn());
    }

    @Test
    public void testSort() throws Exception{
        AlcoholContainer container = new AlcoholContainer();
        for (String alco: alcos){
            container.addInfo(new Alcohol(alco));
        }

        sort(container, Sort.Parameter.Class);
        sort(container, Sort.Parameter.Country);
        sort(container, Sort.Parameter.ID);
        sort(container, Sort.Parameter.Litres);
        sort(container, Sort.Parameter.Name);
        sort(container, Sort.Parameter.Price);
    }
}