package com.vaadin.shoppingcart.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.Position;
import com.vaadin.shoppingcart.component.ShoppingCart;
import com.vaadin.shoppingcart.component.ShoppingCartItem;
import com.vaadin.shoppingcart.dto.Appliances;
import com.vaadin.shoppingcart.dto.Books;
import com.vaadin.shoppingcart.dto.Clothings;
import com.vaadin.shoppingcart.dto.Tools;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Theme("demo")
@Title("ShoppingCart Add-on Demo")
public class DemoUI extends UI
{

    static ArrayList<Clothings> clothings;
    static ArrayList<Books> books;
    static ArrayList<Tools> tools;
    static ArrayList<Appliances> appliances;
    ShoppingCart myCar;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
        @Override
        protected void servletInitialized()throws ServletException {
            super.servletInitialized();

            //Load Items into the Page
            try {

                File fXmlFile = new File("src/main/resources/MerchItems.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("item");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        if(eElement.getAttribute("id").startsWith("10")){
                            if(clothings==null){
                                clothings = new ArrayList();
                            }
                            clothings.add(this.createClothings(eElement));
                        }else if(eElement.getAttribute("id").startsWith("20")){
                            if(books==null){
                                books = new ArrayList();
                            }
                            books.add(this.createBooks(eElement));
                        }else if(eElement.getAttribute("id").startsWith("30")){
                            if(tools==null){
                                tools = new ArrayList();
                            }
                            tools.add(this.createTools(eElement));
                        }else if(eElement.getAttribute("id").startsWith("40")){
                            if(appliances==null){
                                appliances = new ArrayList();
                            }
                            appliances.add(this.createAppliances(eElement));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static Clothings createClothings(Element el){
            try{
                Clothings cl = new Clothings();
                cl.setId(Long.parseLong(el.getAttribute("id")));
                cl.setName(el.getElementsByTagName("name").item(0).getTextContent());
                cl.setUnitPrice(Float.parseFloat(el.getElementsByTagName("price").item(0).getTextContent()));
                cl.setSize(Float.parseFloat(el.getElementsByTagName("size").item(0).getTextContent()));
                cl.setYearsGuarantee(Integer.parseInt(el.getElementsByTagName("yearsGuarantee").item(0).getTextContent()));

                switch (el.getElementsByTagName("brand").item(0).getTextContent()){
                    case "Adidas":
                        cl.setBrand(Clothings.Brand.ADIDAS);
                        break;
                    case "HandM":
                        cl.setBrand(Clothings.Brand.H_AND_M);
                        break;
                    case "Lacoste":
                        cl.setBrand(Clothings.Brand.LACOSTE);
                        break;
                    case "Zahra":
                        cl.setBrand(Clothings.Brand.ZAHRA);
                        break;
                    case "Stadium":
                        cl.setBrand(Clothings.Brand.STADIUM);
                        break;
                    case "Nike":
                        cl.setBrand(Clothings.Brand.NIKE);
                        break;
                    default:
                        System.out.println("Invalid Brand for "+el.getAttribute("id"));
                    break;
                }
                return cl;
            }catch(Exception ex){
                System.out.println("There was a problem creating Clothings Id: "+el.getAttribute("id"));
            }
            return null;
        }


        private static Books createBooks(Element el){
            try{
                Books bk = new Books();
                bk.setId(Long.parseLong(el.getAttribute("id")));
                bk.setName(el.getElementsByTagName("name").item(0).getTextContent());
                bk.setUnitPrice(Float.parseFloat(el.getElementsByTagName("price").item(0).getTextContent()));
                String dateS = el.getElementsByTagName("publishDate").item(0).getTextContent();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

                try {
                    Date date = formatter.parse(dateS);
                    bk.setPublishDate(date);
                } catch (Exception e) {
                    System.out.println("Invalid Date format (yyyy-mm-dd) for Book Id: "+el.getAttribute("id"));
                }

                switch (el.getElementsByTagName("brand").item(0).getTextContent()){
                    case "Biography":
                        bk.setBookType(Books.Type.BIOGRAPHY);
                        break;
                    case "Fable":
                        bk.setBookType(Books.Type.FABLE);
                        break;
                    case "Fantasy":
                        bk.setBookType(Books.Type.FANTASY);
                        break;
                    case "Folk":
                        bk.setBookType(Books.Type.FOLK_TALE);
                        break;
                    case "Legend":
                        bk.setBookType(Books.Type.LEGEND);
                        break;
                    case "Myth":
                        bk.setBookType(Books.Type.MYTH);
                        break;
                    case "Science Fiction":
                        bk.setBookType(Books.Type.SCIENCE_FICTION);
                        break;
                    default:
                        System.out.println("Invalid Type of Book for "+el.getAttribute("id"));
                        break;
                }
                return bk;
            }catch(Exception ex){
                System.out.println("There was a problem creating Books Id: "+el.getAttribute("id"));
            }
            return null;
        }

        private static Tools createTools(Element el){
            try{
                Tools tool = new Tools();
                tool.setId(Long.parseLong(el.getAttribute("id")));
                tool.setName(el.getElementsByTagName("name").item(0).getTextContent());
                tool.setUnitPrice(Float.parseFloat(el.getElementsByTagName("price").item(0).getTextContent()));
                return tool;
            }catch(Exception ex){
                System.out.println("There was a problem creating Tools Id: "+el.getAttribute("id"));
            }
            return null;
        }

        private static Appliances createAppliances(Element el){
            try{
                Appliances app = new Appliances();
                app.setId(Long.parseLong(el.getAttribute("id")));
                app.setName(el.getElementsByTagName("name").item(0).getTextContent());
                app.setUnitPrice(Float.parseFloat(el.getElementsByTagName("price").item(0).getTextContent()));

                switch (el.getElementsByTagName("brand").item(0).getTextContent()){
                    case "Toshiba":
                        app.setBrand(Appliances.Brand.TOSHIBA);
                        break;
                    case "Panasonic":
                        app.setBrand(Appliances.Brand.PANASONIC);
                        break;
                    case "Sony":
                        app.setBrand(Appliances.Brand.SONY);
                        break;
                    case "Casio":
                        app.setBrand(Appliances.Brand.CASIO);
                        break;
                    case "Lenovo":
                        app.setBrand(Appliances.Brand.LENOVO);
                        break;
                    case "Samsung":
                        app.setBrand(Appliances.Brand.SAMSUNG);
                        break;
                    case "Genius":
                        app.setBrand(Appliances.Brand.GENIUS);
                        break;
                    case "Bose":
                        app.setBrand(Appliances.Brand.BOSE);
                        break;
                    default:
                        System.out.println("Invalid brand for Appliance "+el.getAttribute("id"));
                        break;
                }
                return app;
            }catch(Exception ex){
                System.out.println("There was a problem creating Appliance Id: "+el.getAttribute("id"));
            }
            return null;
        }

    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //We define default layout for our page
        final GridLayout grid = new GridLayout(5,5);

        //We use labels to show information
        Label jacketLabel = new Label("Jackets");
        Label jerseysLabel = new Label("Jerseys");
        Label pantsLabel = new Label("Pants");

        if(clothings!=null && !clothings.isEmpty()){
            Label clothingsLabel = new Label("Clothings");
            grid.addComponent(clothingsLabel, 0, 0);
            int cont = 0;
            for(Clothings clothes : clothings){
                if(clothes!=null){
                    cont++;
                    GridLayout vertical = new GridLayout(2,4);
                    Label nameLabel = new Label("Name:");
                    Label priceLabel = new Label("Price:");
                    Label brandLabel = new Label("Brand:");
                    Label name = new Label(clothes.getName());
                    Label price = new Label(String.valueOf(clothes.getUnitPrice()));
                    Label brand = new Label(clothes.getBrand().getStringValue());
                    GridLayout itemForm = this.createShoppingCartForm(clothes);
                    vertical.addComponent(nameLabel, 0,0);
                    vertical.addComponent(name, 1,0);
                    vertical.addComponent(priceLabel, 0,1);
                    vertical.addComponent(price, 1,1);
                    vertical.addComponent(brandLabel, 0,2);
                    vertical.addComponent(brand, 1,2);
                    vertical.addComponent(itemForm, 0,3, 1,3);
                    grid.addComponent(vertical, cont, 0);
                }
            }
        }

        if(books!=null && !books.isEmpty()){
            Label booksLabel = new Label("Books");
            grid.addComponent(booksLabel, 0, 1);
            int cont = 0;
            for(Books book : books){
                if(book!=null){
                    cont++;
                    GridLayout vertical = new GridLayout(2,4);
                    Label nameLabel = new Label("Name:");
                    Label priceLabel = new Label("Price:");
                    Label typeLabel = new Label("Type:");
                    Label name = new Label(book.getName());
                    Label price = new Label(String.valueOf(book.getUnitPrice()));
                    Label type = new Label(book.getBookType().getStringValue());
                    GridLayout itemForm = this.createShoppingCartForm(book);
                    vertical.addComponent(nameLabel, 0,0);
                    vertical.addComponent(name, 1,0);
                    vertical.addComponent(priceLabel, 0,1);
                    vertical.addComponent(price, 1,1);
                    vertical.addComponent(typeLabel, 0,2);
                    vertical.addComponent(type, 1,2);
                    vertical.addComponent(itemForm, 0,3,1,3);
                    grid.addComponent(vertical, cont, 1);
                }
            }
        }

        if(tools!=null && !tools.isEmpty()){
            Label toolsLabel = new Label("Tools");
            grid.addComponent(toolsLabel, 0, 2);
            int cont = 0;
            for(Tools tool : tools){
                if(tool!=null){
                    cont++;
                    GridLayout vertical = new GridLayout(2, 3);
                    Label nameLabel = new Label("Name:");
                    Label priceLabel = new Label("Price:");
                    Label name = new Label(tool.getName());
                    Label price = new Label(String.valueOf(tool.getUnitPrice()));
                    GridLayout itemForm = this.createShoppingCartForm(tool);
                    vertical.addComponent(nameLabel, 0,0);
                    vertical.addComponent(name, 1, 0);
                    vertical.addComponent(priceLabel, 0,1);
                    vertical.addComponent(price, 1, 1);
                    vertical.addComponent(itemForm, 0, 2, 1, 2);
                    grid.addComponent(vertical, cont, 2);
                }
            }
        }

        if(appliances!=null && !appliances.isEmpty()){
            Label appliancesLabel = new Label("Appliances");
            grid.addComponent(appliancesLabel, 0, 3);
            int cont = 0;
            for(Appliances appliance : appliances){
                if(appliance!=null){
                    cont++;
                    GridLayout vertical = new GridLayout(2, 4);
                    Label nameLabel = new Label("Name:");
                    Label priceLabel = new Label("Price:");
                    Label brandLabel = new Label("Brand:");
                    Label name = new Label(appliance.getName());
                    Label price = new Label(String.valueOf(appliance.getUnitPrice()));
                    Label brand = new Label(appliance.getBrand().getStringValue());
                    GridLayout itemForm = this.createShoppingCartForm(appliance);
                    vertical.addComponent(nameLabel, 0, 0);
                    vertical.addComponent(name, 1, 0);
                    vertical.addComponent(priceLabel, 0, 1);
                    vertical.addComponent(price, 1, 1);
                    vertical.addComponent(brandLabel, 0, 2);
                    vertical.addComponent(brand, 1, 2);
                    vertical.addComponent(itemForm, 0, 3, 1, 3);
                    grid.addComponent(vertical, cont, 3);
                }
            }
        }

        //Try to recover ShoppingCart from session.
        ShoppingCart myCart = ShoppingCart.recoverFromSession();
        if(myCart==null){
            //If there is no shoppingcart in my session, define a new component of type ShoppingCart
            myCart = new ShoppingCart();
        }
        myCar = myCart;
        myCar.setShoppingCartStyles(ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_NO_VERTICAL_LINES);

        //In this attribute we should set the uri to payment page.
        myCar.setUriToCheckout("http://vaadin.org/");

        //Define a button component to hide and show the ShoppingCart.
        Button showSC = new Button("Show My ShoppingCart");
        //Add a click listener to our button...
        showSC.addClickListener( e -> {
            Window window = new Window();
            window.setContent(myCar);
            window.setModal(true);
            window.setSizeFull();
            getUI().addWindow(window);
            myCar.renderShoppingCart();
        });

        grid.addComponent(showSC, 0,4);



        //Look & Feel stuff
        grid.setMargin(true);
        grid.setSpacing(true);
        grid.setSizeFull();

        //Not sure, but it seems this connect our Layout with the page displayed
        setContent(grid);
    }


    private GridLayout createShoppingCartForm(ShoppingCartItem item){
        GridLayout layout = new GridLayout(2,1);
        TextField qty = new TextField();
        qty.setId("qty_"+item.getId());
        qty.setCaption("Qty:");
        qty.setValue("1");

        Button btn = new Button("Add to Cart");
        btn.addClickListener( e -> {
            if(qty.getValue().isEmpty() || qty.getValue().equals("0")){
                this.showError("Please, specify a valid quantity.");
            }else{
                int quantity = 0;
                try{
                    quantity = Integer.parseInt(qty.getValue());
                    if(quantity<0){
                        this.showError("Invalid quantity value");
                    }else{
                        ShoppingCartItem newItem = new ShoppingCartItem(item.getId(), item.getName(), item.getUnitPrice(), quantity);
                        myCar.addItemToShoppingCart(newItem);
                    }
                }catch(Exception ex){
                    this.showError("Invalid quantity value");
                }
            }
        });
        layout.addComponent(qty, 0,0);
        layout.addComponent(btn, 1,0);
        return layout;
    }

    private void showError(String s){
        Notification notification = new Notification("Error",
                s, Notification.TYPE_ERROR_MESSAGE, true);
        notification.setPosition(Position.TOP_CENTER);
        notification.show(Page.getCurrent());
    }
}
