package com.example.miles.nckuguiding.Classroom;

import android.content.Context;
import android.content.res.AssetManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by miles on 2016/10/21.
 */
public class SearchClassroom {
    public static String[] RoomInfo = new String[3];
    public static String[] BuildingList;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;

    public SearchClassroom(Context context) {
        Initialize(context);
    }

    public void Initialize(Context context) {
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open("NCKUBuildingDatabase.xml");
            this.dbFactory = DocumentBuilderFactory.newInstance();
            this.dBuilder = this.dbFactory.newDocumentBuilder();
            this.doc = this.dBuilder.parse(is);
            this.doc.getDocumentElement().normalize();
            XPath e = XPathFactory.newInstance().newXPath();
            String expression = "/NCKUClassroomDatabase/Building";
            NodeList nodeList = (NodeList)e.compile(expression).evaluate(this.doc, XPathConstants.NODESET);
            BuildingList = new String[nodeList.getLength()];

            for(int i = 0; i < nodeList.getLength(); ++i) {
                Node nNode = nodeList.item(i);
                Element eElement = (Element)nNode;
                BuildingList[i] = eElement.getAttribute("CHName");
            }
        } catch (XPathExpressionException var7) {
            var7.printStackTrace();
        } catch (ParserConfigurationException var8) {
            var8.printStackTrace();
        } catch (SAXException var9) {
            var9.printStackTrace();
        } catch (IOException var10) {
            var10.printStackTrace();
        }

    }

    private String getBuildingName(String BuildingCode) {
        try {
            XPath e = XPathFactory.newInstance().newXPath();
            String expression = "/NCKUClassroomDatabase/Building[@code=\'" + BuildingCode + "\']";
            NodeList nodeList = (NodeList)e.compile(expression).evaluate(this.doc, XPathConstants.NODESET);
            if(nodeList.getLength() == 0) {
                return "找不到此樓";
            } else {
                Node nNode = nodeList.item(0);
                Element eElement = (Element)nNode;
                return eElement.getElementsByTagName("BuildingName").item(0).getTextContent();
            }
        } catch (XPathExpressionException var7) {
            var7.printStackTrace();
            return "找不到此樓";
        }
    }

    private String getFloorName(String BuildingCode, String FloorCode) {
        try {
            XPath e = XPathFactory.newInstance().newXPath();
            String expression = "/NCKUClassroomDatabase/Building[@code=\'" + BuildingCode + "\']/Floor[@code=\'" + FloorCode + "\']";
            NodeList nodeList = (NodeList)e.compile(expression).evaluate(this.doc, XPathConstants.NODESET);
            if(nodeList.getLength() == 0) {
                return "找不到該樓層";
            } else {
                Node nNode = nodeList.item(0);
                Element eElement = (Element)nNode;
                return eElement.getElementsByTagName("FloorName").item(0).getTextContent();
            }
        } catch (XPathExpressionException var8) {
            var8.printStackTrace();
            return "找不到該樓層";
        }
    }

    private String getClassroomName(String BuildingCode, String FloorCode, String ClassroomCode) {
        try {
            XPath e = XPathFactory.newInstance().newXPath();
            String expression = "/NCKUClassroomDatabase/Building[@code=\'" + BuildingCode + "\']/Floor[@code=\'" + FloorCode + "\']/Classroom[@code=\'" + ClassroomCode + "\']";
            NodeList nodeList = (NodeList)e.compile(expression).evaluate(this.doc, XPathConstants.NODESET);
            if(nodeList.getLength() == 0) {
                return "找不到該教室";
            } else {
                Node nNode = nodeList.item(0);
                Element eElement = (Element)nNode;
                return eElement.getElementsByTagName("ClassroomName").item(0).getTextContent();
            }
        } catch (XPathExpressionException var9) {
            var9.printStackTrace();
            return "找不到該教室";
        }
    }

    public String getCodebyCHName(String CHName) {
        try {
            XPath e = XPathFactory.newInstance().newXPath();
            String expression = "/NCKUClassroomDatabase/Building[@CHName=\'" + CHName + "\']";
            NodeList nodeList = (NodeList)e.compile(expression).evaluate(this.doc, XPathConstants.NODESET);
            if(nodeList.getLength() == 0) {
                return "找不到此樓";
            } else {
                Node nNode = nodeList.item(0);
                Element eElement = (Element)nNode;
                return eElement.getAttribute("code");
            }
        } catch (XPathExpressionException var7) {
            var7.printStackTrace();
            return "找不到此樓";
        }
    }

    public String[] getRoomInfobyClassroomName(String ClassroomName) {
        RoomInfo[0] = "找不到此教室";
        RoomInfo[1] = "找不到此教室";
        RoomInfo[2] = "找不到此教室";

        try {
            XPath e = XPathFactory.newInstance().newXPath();
            String expression = "/NCKUClassroomDatabase/Building";
            NodeList nodeList = (NodeList)e.compile(expression).evaluate(this.doc, XPathConstants.NODESET);

            int i;
            Node nNode;
            Element eElement;
            NodeList nodeList2;
            int j;
            Node nNode2;
            Element eElement2;
            for(i = 0; i < nodeList.getLength(); ++i) {
                nNode = nodeList.item(i);
                eElement = (Element)nNode;
                nodeList2 = eElement.getElementsByTagName("Classroom");

                for(j = 0; j < nodeList2.getLength(); ++j) {
                    nNode2 = nodeList2.item(j);
                    eElement2 = (Element)nNode2;
                    if(ClassroomName.equals(eElement2.getElementsByTagName("ClassroomName").item(0).getTextContent())) {
                        if(eElement2.getElementsByTagName("FloorName").getLength() >= 1) {
                            RoomInfo[0] = eElement.getAttribute("CHName");
                            RoomInfo[1] = eElement2.getElementsByTagName("FloorName").item(0).getTextContent();
                            RoomInfo[2] = eElement2.getElementsByTagName("ClassroomName").item(0).getTextContent();
                            return RoomInfo;
                        }

                        i = nodeList.getLength();
                        j = nodeList2.getLength();
                    }
                }
            }

            for(i = 0; i < nodeList.getLength(); ++i) {
                nNode = nodeList.item(i);
                eElement = (Element)nNode;
                nodeList2 = eElement.getElementsByTagName("Floor");

                for(j = 0; j < nodeList2.getLength(); ++j) {
                    nNode2 = nodeList2.item(j);
                    eElement2 = (Element)nNode2;
                    NodeList nodeList3 = eElement2.getElementsByTagName("Classroom");

                    for(int k = 0; k < nodeList3.getLength(); ++k) {
                        Node nNode3 = nodeList3.item(k);
                        Element eElement3 = (Element)nNode3;
                        if(ClassroomName.equals(eElement3.getElementsByTagName("ClassroomName").item(0).getTextContent())) {
                            RoomInfo[0] = eElement.getAttribute("CHName");
                            RoomInfo[1] = eElement2.getElementsByTagName("FloorName").item(0).getTextContent();
                            RoomInfo[2] = eElement3.getElementsByTagName("ClassroomName").item(0).getTextContent();
                            return RoomInfo;
                        }
                    }
                }
            }
        } catch (XPathExpressionException var16) {
            var16.printStackTrace();
        }

        return RoomInfo;
    }

    public String[] getRoomInfo(String code) {
        RoomInfo[0] = "錯誤的代碼";
        RoomInfo[1] = "錯誤的代碼";
        RoomInfo[2] = "錯誤的代碼";
        String BuildingCode;
        if(code.length() == 5) {
            BuildingCode = code.substring(0, 2);
            String FloorCode = code.substring(2, 3);
            String ClassroomCode = code.substring(3, code.length());
            RoomInfo[0] = this.getBuildingName(BuildingCode);
            RoomInfo[1] = this.getFloorName(BuildingCode, FloorCode);
            RoomInfo[2] = this.getClassroomName(BuildingCode, FloorCode, ClassroomCode);
        } else if(code.length() == 4) {
            if(code.substring(0, 1).equals("7")) {
                RoomInfo[0] = "唯農大樓";
                BuildingCode = "11";
            } else {
                BuildingCode = code.substring(0, 2);
                RoomInfo[0] = this.getBuildingName(BuildingCode);
            }

            try {
                XPath e = XPathFactory.newInstance().newXPath();
                String expression = "/NCKUClassroomDatabase/Building[@code=\'" + BuildingCode + "\']/Classroom[@code=\'" + code + "\']";
                NodeList nodeList = (NodeList)e.compile(expression).evaluate(this.doc, XPathConstants.NODESET);
                if(nodeList.getLength() == 0) {
                    RoomInfo[1] = "找不到該教室";
                    RoomInfo[2] = "找不到該教室";
                    return RoomInfo;
                }

                Node nNode = nodeList.item(0);
                Element eElement = (Element)nNode;
                RoomInfo[1] = eElement.getElementsByTagName("FloorName").item(0).getTextContent();
                RoomInfo[2] = eElement.getElementsByTagName("ClassroomName").item(0).getTextContent();
                return RoomInfo;
            } catch (XPathExpressionException var10) {
                var10.printStackTrace();
            }
        }

        return RoomInfo;
    }

    public String[] getRoomInfo(String CHName, String code) {
        RoomInfo[0] = "錯誤的代碼";
        RoomInfo[1] = "錯誤的代碼";
        RoomInfo[2] = "錯誤的代碼";

        try {
            XPath e = XPathFactory.newInstance().newXPath();
            String expression = "/NCKUClassroomDatabase/Building[@CHName=\'" + CHName + "\']";
            NodeList nodeList = (NodeList)e.compile(expression).evaluate(this.doc, XPathConstants.NODESET);
            if(nodeList.getLength() != 0) {
                if(code.length() == 5) {
                    RoomInfo = this.getRoomInfo(code);
                    if(RoomInfo[0].equals(CHName)) {
                        return RoomInfo;
                    }

                    RoomInfo[1] = "找不到該教室";
                    RoomInfo[2] = "找不到該教室";
                }

                RoomInfo[0] = CHName;
                expression = "/NCKUClassroomDatabase/Building[@CHName=\'" + CHName + "\']/Classroom[@code=\'" + code + "\']";
                nodeList = (NodeList)e.compile(expression).evaluate(this.doc, XPathConstants.NODESET);
                if(nodeList.getLength() == 0) {
                    RoomInfo[1] = "找不到該教室";
                    RoomInfo[2] = "找不到該教室";
                    return RoomInfo;
                } else {
                    Node nNode = nodeList.item(0);
                    RoomInfo[0] = CHName;
                    Element eElement = (Element)nNode;
                    RoomInfo[1] = eElement.getElementsByTagName("FloorName").item(0).getTextContent();
                    RoomInfo[2] = eElement.getElementsByTagName("ClassroomName").item(0).getTextContent();
                    return RoomInfo;
                }
            } else {
                RoomInfo[0] = "找不到該樓";
                RoomInfo[1] = "";
                RoomInfo[2] = "";
                return RoomInfo;
            }
        } catch (XPathExpressionException var8) {
            var8.printStackTrace();
            return RoomInfo;
        }
    }
}
