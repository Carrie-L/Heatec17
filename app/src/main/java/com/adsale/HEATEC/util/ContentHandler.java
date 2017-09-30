package com.adsale.HEATEC.util;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.dao.Exhibitor;
import com.adsale.HEATEC.dao.ExhibitorIndustryDtl;
import com.adsale.HEATEC.dao.Floor;
import com.adsale.HEATEC.dao.Industry;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.dao.MapFloor;
import com.adsale.HEATEC.dao.News;
import com.adsale.HEATEC.dao.NewsLink;
import com.adsale.HEATEC.dao.WebContent;
import com.adsale.HEATEC.data.LoadRepository;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;

import sanvio.libs.util.FileUtils;

public class ContentHandler extends DefaultHandler {

    private static final String TAG = "ContentHandler";
    // News
    private String nodeName;

    public ArrayList<MainIcon> mMainIcons;
    private ArrayList<MapFloor> mMapFloors;
    private ArrayList<NewsLink> mNewsLinks;
    public ArrayList<WebContent> mWebContents;
    private ArrayList<News> mNewsArrayLists;
    private ArrayList<Industry> mIndustries;
    private ArrayList<Floor> mFloors;
    private ArrayList<Exhibitor> mExhibitors;
    private ArrayList<ExhibitorIndustryDtl> mIndustryDtls;

    private News mNews;
    private MainIcon mMainIcon;
    private MapFloor mMapFloor;
    private NewsLink mNewsLink;
    private WebContent mWebContent;
    private Industry mIndustry;
    private Floor mFloor;
    private Exhibitor mExhibitor;
    private ExhibitorIndustryDtl mIndustryDtl;

    private String strData;
    private StringBuffer sb;
    private boolean isNews;
    private boolean isNewsLink;
    private boolean isWebContent;
    private boolean isMainIcon;
    private boolean isMapFloor;
    private boolean isIndustry;
    private boolean isExhibitor;
    private boolean isExhibitorDtl;
    private boolean isFloor;

    private long startTime;
    private long endTime;

    private LoadRepository mLoadRepository;

    public static ContentHandler getInstance(LoadRepository repository) {
        return new ContentHandler(repository);
    }

    private ContentHandler(LoadRepository repository) {
        mLoadRepository = repository;
    }


    /**
     * SAX解析XML
     *
     * @param <>
     * @param xmlData
     * @return
     */
    public boolean parseXmlWithSAX(String xmlData) {
        FileUtils.writeFileToSD(xmlData, App.RootDir + "response.xml");

        if (xmlData == null) {
            LogUtil.e(TAG, "xmlData==null，直接返回");
            return false;
        } else {
            long startTime = System.currentTimeMillis();
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                XMLReader xmlReader = factory.newSAXParser().getXMLReader();
                // 将ContentHandler 的实例设置到XMLReader 中
                xmlReader.setContentHandler(this);
                // 开始执行解析
                xmlReader.parse(new InputSource(new StringReader(xmlData)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            LogUtil.i(TAG, "parseXmlWithSAX spend time: " + (endTime - startTime) + " ms");
            return true;
        }
    }

    @Override
    public void startDocument() throws SAXException {
        LogUtil.i("startDocument------>", "解析开始");
        startTime = System.currentTimeMillis();
        sb = new StringBuffer();
        mMainIcons = new ArrayList<>();
        mMapFloors = new ArrayList<>();
        mNewsLinks = new ArrayList<>();
        mWebContents = new ArrayList<>();
        mNewsArrayLists = new ArrayList<>();
        mExhibitors = new ArrayList<>();
        mIndustryDtls = new ArrayList<>();
        mIndustries = new ArrayList<>();
        mFloors = new ArrayList<>();
    }

    public ArrayList<News> parseNewsXml(String xmlData, ArrayList<News> list) {
        if (xmlData == null) {
            LogUtil.e(TAG, "xmlData==null，直接返回");
            return list;
        } else {
            long startTime = System.currentTimeMillis();
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                XMLReader xmlReader = factory.newSAXParser().getXMLReader();
                // 将ContentHandler 的实例设置到XMLReader 中
                xmlReader.setContentHandler(this);
                // 开始执行解析
                xmlReader.parse(new InputSource(new StringReader(xmlData)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            LogUtil.i(TAG, "parseNewsXml spend time: " + (endTime - startTime) + " ms");
            return mNewsArrayLists;
        }
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        sb.delete(0, sb.length());
        nodeName = localName;

        if ("News".equals(localName)) {
            mNews = new News();
            isNews = true;
        } else if (localName.equals("NewsLink")) {
            mNewsLink = new NewsLink();
            isNewsLink = true;
        } else if (localName.equals("WebContent")) {
            mWebContent = new WebContent();
            isWebContent = true;
        } else if (localName.equals("MainIcon")) {
            mMainIcon = new MainIcon();
            isMainIcon = true;
        } else if (localName.equals("MapFloor")) {
            mMapFloor = new MapFloor();
            isMapFloor = true;
        } else if (localName.equals("Industry")) {
            mIndustry = new Industry();
            isIndustry = true;
        } else if (localName.equals("Exhibitor")) {
            mExhibitor = new Exhibitor();
            isExhibitor = true;
        } else if (localName.equals("ExhibitorIndustryDtl")) {
            mIndustryDtl = new ExhibitorIndustryDtl();
            isExhibitorDtl = true;
        } else if (localName.equals("ExFloor")) {
            mFloor = new Floor();
            isFloor = true;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        sb.append(ch, start, length);
        strData = sb.toString();

        if (isMainIcon) {
            mainIcon();
        }

        if (isNewsLink) {
            newsLink();
        }

        if (isNews) {
            news();
        }

        if (isMapFloor) {
            mapFloor();
        }

        webContent();

        if(isExhibitor){
            exhibitor();
        }

        if(isExhibitorDtl){
            exhibitorDtl();
        }

        if(isFloor){
            floor();
        }

        if(isIndustry){
            industry();
        }
    }

    private void mainIcon() {
        if ("IconID".equals(nodeName)) {
            mMainIcon.setIconID(strData);
        } else if ("CType".equals(nodeName)) {
            if(strData.isEmpty()){
                strData="0";
            }
            mMainIcon.setCType(Integer.valueOf(strData));
        } else if ("IsDelete".equals(nodeName)) {
            mMainIcon.setIsDelete(!strData.equals("false"));
        }else if ("SEQ".equals(nodeName)) {
            mMainIcon.setSEQ(Integer.valueOf(strData));
        } else if ("IsHidden".equals(nodeName)) {
            strData = strData.toLowerCase().equals("false") ? "0" : "1";
            mMainIcon.setIsHidden(Integer.valueOf(strData));
        } else if ("TitleTW".equals(nodeName)) {
            mMainIcon.setTitleTW(strData);
        } else if ("TitleCN".equals(nodeName)) {
            mMainIcon.setTitleCN(strData);
        } else if ("TitleEN".equals(nodeName)) {
            mMainIcon.setTitleEN(strData);
        } else if ("Icon".equals(nodeName)) {
            mMainIcon.setIcon(strData);
        } else if ("CFile".equals(nodeName)) {
            mMainIcon.setCFile(strData);
        } else if ("ZipDateTime".equals(nodeName)) {
            mMainIcon.setZipDateTime(strData);
        } else if ("BaiDu_TJ".equals(nodeName)) {
            mMainIcon.setBaiDu_TJ(strData);
        } else if ("Google_TJ".equals(nodeName)) {
            mMainIcon.setGoogle_TJ(strData);
        } else if (nodeName.equals("CreateDateTime")) {
            mMainIcon.setCreateDateTime(strData);
        } else if (nodeName.equals("UpdateDateTime")) {
            mMainIcon.setUpdateDateTime(strData);
        } else if (nodeName.equals("RecordTimeStamp")) {
            mMainIcon.setRecordTimeStamp(strData);
        }
        mMainIcon.setIsDown(0);
    }

    private void newsLink() {
        if ("LinkID".equals(nodeName)) {
            mNewsLink.setLinkID(strData);
        } else if ("NewsID".equals(nodeName)) {
            mNewsLink.setNewsID(strData);
        }else if ("IsDelete".equals(nodeName)) {
            mNewsLink.setIsDelete(!strData.equals("false"));
        } else if ("Photo".equals(nodeName)) {
            mNewsLink.setPhoto(strData);
        } else if ("Link".equals(nodeName)) {
            mNewsLink.setLink(strData);
        } else if ("Title".equals(nodeName)) {
            mNewsLink.setTitle(strData);
        } else if (nodeName.equals("CreateDateTime")) {
            mNewsLink.setCreateDateTime(strData);
        } else if (nodeName.equals("UpdateDateTime")) {
            mNewsLink.setUpdateDateTime(strData);
        } else if (nodeName.equals("RecordTimeStamp")) {
            mNewsLink.setRecordTimeStamp(strData);
        }
    }

    private void news() {
        if ("LType".equals(nodeName)) {
            if(strData.isEmpty()){
                strData="1";
            }
            mNews.setLType(Integer.parseInt(strData));
        } else if ("NewsID".equals(nodeName)) {
            mNews.setNewsID(strData);
        }else if ("IsDelete".equals(nodeName)) {
            mNews.setIsDelete(!strData.equals("false"));
        } else if ("Description".equals(nodeName)) {
            mNews.setDescription(strData);
        } else if ("Logo".equals(nodeName)) {
            mNews.setLogo(strData);
        } else if ("Title".equals(nodeName)) {
            mNews.setTitle(strData);
        } else if ("ShareLink".equals(nodeName)) {
            mNews.setShareLink(strData);
        } else if ("PublishDate".equals(nodeName)) {
            mNews.setPublishDate(strData);
        } else if (nodeName.equals("CreateDateTime")) {
            mNews.setCreateDateTime(strData);
        } else if (nodeName.equals("UpdateDateTime")) {
            mNews.setUpdateDateTime(strData);
        } else if (nodeName.equals("RecordTimeStamp")) {
            mNews.setRecordTimeStamp(strData);
        }
    }

    private void mapFloor() {
        if ("MapFloorID".equals(nodeName)) {
            mMapFloor.setMapFloorID(strData);
        }else if ("IsDelete".equals(nodeName)) {
            mMapFloor.setIsDelete(!strData.equals("false"));
        }else if ("SEQ".equals(nodeName)) {
            mMapFloor.setSEQ(Integer.parseInt(strData));
        } else if ("Type".equals(nodeName)) {
            mMapFloor.setType(Integer.parseInt(strData));
        } else if ("ParentID".equals(nodeName)) {
            if(strData==null||strData.isEmpty()){
                strData="";
            }
            mMapFloor.setParentID(strData);
        } else if ("NameTW".equals(nodeName)) {
            mMapFloor.setNameTW(strData);
        } else if ("NameCN".equals(nodeName)) {
            mMapFloor.setNameCN(strData);
        } else if ("NameEN".equals(nodeName)) {
            mMapFloor.setNameEN(strData);
        } else if (nodeName.equals("CreateDateTime")) {
            mMapFloor.setCreateDateTime(strData);
        } else if (nodeName.equals("UpdateDateTime")) {
            mMapFloor.setUpdateDateTime(strData);
        } else if (nodeName.equals("RecordTimeStamp")) {
            mMapFloor.setRecordTimeStamp(strData);
        }
    }

    private void webContent() {
        if (isWebContent) {
            if ("IsDelete".equals(nodeName)) {
                mWebContent.setIsDelete(!strData.equals("false"));
            }else if ("PageId".equals(nodeName)) {
                mWebContent.setPageId(strData);
            } else if ("CType".equals(nodeName)) {
                mWebContent.setCType(Integer.parseInt(strData));
            } else if ("TitleTW".equals(nodeName)) {
                mWebContent.setTitleTW(strData);
            } else if ("TitleCN".equals(nodeName)) {
                mWebContent.setTitleCN(strData);
            } else if ("TitleEN".equals(nodeName)) {
                mWebContent.setTitleEN(strData);
            } else if ("CFile".equals(nodeName)) {
                mWebContent.setCFile(strData);
            } else if ("ZipDateTime".equals(nodeName)) {
                mWebContent.setZipDateTime(strData);
            } else if ("ContentEN".equals(nodeName)) {
                mWebContent.setContentEN(strData);
            } else if ("ContentSC".equals(nodeName)) {
                //	LogUtil.i(TAG, "contentSC="+strData);
                mWebContent.setContentSC(strData);
            } else if ("ContentTC".equals(nodeName)) {
                mWebContent.setContentTC(strData);
            } else if (nodeName.equals("CreateDateTime")) {
                mWebContent.setCreateDateTime(strData);
            } else if (nodeName.equals("UpdateDateTime")) {
                mWebContent.setUpdateDateTime(strData);
            } else if (nodeName.equals("RecordTimeStamp")) {
                mWebContent.setRecordTimeStamp(strData);
            }
        }
    }

    private void exhibitor(){
        if(strData==null||strData.isEmpty()){
            strData="";
        }
        if ("IsDelete".equals(nodeName)) {
            mExhibitor.setIsDelete(!strData.equals("false"));
        } else if ("CompanyID".equals(nodeName)) {
            mExhibitor.setCompanyID(strData);
        } else if ("Floor".equals(nodeName)) {
            mExhibitor.setFloor(strData);
        } else if ("ExhibitorNO".equals(nodeName)) {
            mExhibitor.setExhibitorNO(strData);
        } else if ("CountryID".equals(nodeName)) {
            mExhibitor.setCountryID(strData);
        } else if ("CompanyNameTW".equals(nodeName)) {
            mExhibitor.setCompanyNameTW(strData);
        }else if ("DescriptionTW".equals(nodeName)) {
            mExhibitor.setDescriptionTW(strData);
        }else if ("AddressTW".equals(nodeName)) {
            mExhibitor.setAddressTW(strData);
        }else if ("ContactTW".equals(nodeName)) {
            mExhibitor.setContactTW(strData);
        }else if ("TitleTW".equals(nodeName)) {
            mExhibitor.setTitleTW(strData);
        }else if ("SortTW".equals(nodeName)) {
            mExhibitor.setSortTW(strData);
        }else if ("CompanyNameCN".equals(nodeName)) {
            mExhibitor.setCompanyNameCN(strData);
        }else if ("DescriptionCN".equals(nodeName)) {
            mExhibitor.setDescriptionCN(strData);
        }else if ("AddressCN".equals(nodeName)) {
            mExhibitor.setAddressCN(strData);
        }else if ("ContactCN".equals(nodeName)) {
            mExhibitor.setContactCN(strData);
        }else if ("TitleCN".equals(nodeName)) {
            mExhibitor.setTitleCN(strData);
        }else if ("SortCN".equals(nodeName)) {
            mExhibitor.setSortCN(strData);
        }else if ("CompanyNameEN".equals(nodeName)) {
            mExhibitor.setCompanyNameEN(strData);
        }else if ("DescriptionEN".equals(nodeName)) {
            mExhibitor.setDescriptionEN(strData);
        }else if ("AddressEN".equals(nodeName)) {
            mExhibitor.setAddressEN(strData);
        }else if ("ContactEN".equals(nodeName)) {
            mExhibitor.setContactEN(strData);
        }else if ("TitleEN".equals(nodeName)) {
            mExhibitor.setTitleEN(strData);
        }else if ("SortEN".equals(nodeName)) {
            mExhibitor.setSortEN(strData);
        }else if ("Logo".equals(nodeName)) {
            mExhibitor.setLogo(strData);
        }else if ("Tel".equals(nodeName)) {
            mExhibitor.setTel(strData);
        }else if ("Tel1".equals(nodeName)) {
            mExhibitor.setTel1(strData);
        }else if ("Fax".equals(nodeName)) {
            mExhibitor.setFax(strData);
        }else if ("Email".equals(nodeName)) {
            mExhibitor.setEmail(strData);
        }else if ("Website".equals(nodeName)) {
            mExhibitor.setWebsite(strData);
        }else if ("Longitude".equals(nodeName)) {
            mExhibitor.setLongitude(strData);
        }else if ("Latitude".equals(nodeName)) {
            mExhibitor.setLatitude(strData);
        }else if ("Location_X".equals(nodeName)) {
            mExhibitor.setLocation_X(strData);
        }else if ("Location_Y".equals(nodeName)) {
            mExhibitor.setLocation_Y(strData);
        }else if ("Location_W".equals(nodeName)) {
            mExhibitor.setLocation_W(strData);
        }else if ("Location_H".equals(nodeName)) {
            mExhibitor.setLocation_H(strData);
        }else if ("SEQ".equals(nodeName)) {
            mExhibitor.setSEQ(strData);
        } else if (nodeName.equals("CreateDateTime")) {
            mExhibitor.setCreateDateTime(strData);
        } else if (nodeName.equals("UpdateDateTime")) {
            mExhibitor.setUpdateDateTime(strData);
        } else if (nodeName.equals("RecordTimeStamp")) {
            mExhibitor.setRecordTimeStamp(strData);
        }
    }

    private void exhibitorDtl(){
        if ("IsDelete".equals(nodeName)) {
            mIndustryDtl.setIsDelete(!strData.equals("false"));
        } else if ("CompanyID".equals(nodeName)) {
            mIndustryDtl.setCompanyID(strData);
        } else if ("IndustryID".equals(nodeName)) {
            mIndustryDtl.setIndustryID(strData);
        } else if (nodeName.equals("CreateDateTime")) {
            mIndustryDtl.setCreateDateTime(strData);
        } else if (nodeName.equals("UpdateDateTime")) {
            mIndustryDtl.setUpdateDateTime(strData);
        } else if (nodeName.equals("RecordTimeStamp")) {
            mIndustryDtl.setRecordTimeStamp(strData);
        }
    }

    private void floor() {
        if ("IsDelete".equals(nodeName)) {
            mFloor.setIsDelete(!strData.equals("false"));
        } else if ("FloorID".equals(nodeName)) {
            mFloor.setFloorID(strData);
        } else if ("SEQ".equals(nodeName)) {
            if(strData.isEmpty()){
                strData="0";
            }
            mFloor.setSEQ(Integer.valueOf(strData));
        } else if ("FloorNameTW".equals(nodeName)) {
            mFloor.setFloorNameTW(strData);
        } else if ("FloorNameCN".equals(nodeName)) {
            mFloor.setFloorNameCN(strData);
        } else if ("FloorNameEN".equals(nodeName)) {
            mFloor.setFloorNameEN(strData);
        } else if (nodeName.equals("CreateDateTime")) {
            mFloor.setCreateDateTime(strData);
        } else if (nodeName.equals("UpdateDateTime")) {
            mFloor.setUpdateDateTime(strData);
        } else if (nodeName.equals("RecordTimeStamp")) {
            mFloor.setRecordTimeStamp(strData);
        }
    }

    private void industry(){
        if(strData.isEmpty()){
            strData="";
        }
        if ("IsDelete".equals(nodeName)) {
            mIndustry.setIsDelete(!strData.equals("false"));
        } else if ("IndustryID".equals(nodeName)) {
            mIndustry.setIndustryID(strData);
        } else if ("SortTW".equals(nodeName)) {
            mIndustry.setSortTW(strData);
        }else if ("SortCN".equals(nodeName)) {
            mIndustry.setSortCN(strData);
        }else if ("SortEN".equals(nodeName)) {
            mIndustry.setSortEN(strData);
        } else if ("IndustryNameTW".equals(nodeName)) {
            mIndustry.setIndustryNameTW(strData);
        } else if ("IndustryNameCN".equals(nodeName)) {
            mIndustry.setIndustryNameCN(strData);
        } else if ("IndustryNameEN".equals(nodeName)) {
            mIndustry.setIndustryNameEN(strData);
        } else if (nodeName.equals("CreateDateTime")) {
            mIndustry.setCreateDateTime(strData);
        } else if (nodeName.equals("UpdateDateTime")) {
            mIndustry.setUpdateDateTime(strData);
        } else if (nodeName.equals("RecordTimeStamp")) {
            mIndustry.setRecordTimeStamp(strData);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if ("News".equals(localName)) {
            mNewsArrayLists.add(mNews);
            isNews = false;
        } else if (localName.equals("NewsLink")) {
            mNewsLinks.add(mNewsLink);
            isNewsLink = false;
        } else if (localName.equals("WebContent")) {
            mWebContents.add(mWebContent);
            isWebContent = false;
        } else if (localName.equals("MainIcon")) {
            if (mMainIcon.getBaiDu_TJ() != null) {
                mMainIcons.add(mMainIcon);
            }
            isMainIcon = false;
        } else if (localName.equals("MapFloor")) {
            mMapFloors.add(mMapFloor);
            isMapFloor = false;
        }else if (localName.equals("Exhibitor")) {
            mExhibitors.add(mExhibitor);
            isExhibitor = false;
        }else if (localName.equals("ExhibitorIndustryDtl")) {
            mIndustryDtls.add(mIndustryDtl);
            isExhibitorDtl = false;
        }else if (localName.equals("Industry")) {
            mIndustries.add(mIndustry);
            isIndustry = false;
        }else if (localName.equals("ExFloor")) {
            mFloors.add(mFloor);
            isFloor = false;
        }
        nodeName = null;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        LogUtil.i("ContentHandler", "解析完成！！！！！！！");
        logList(mMainIcons, "MainIcon");
        logList(mNewsArrayLists, "mNewsArrayLists");
        logList(mNewsLinks, "mNewsLinks");
        logList(mWebContents, "mWebContents");
        logList(mMapFloors, "mMapFloors");
        logList(mExhibitors, "mExhibitors");
        logList(mIndustryDtls, "mIndustryDtls");
        logList(mIndustries, "mIndustries");
        logList(mFloors, "mFloors");

        endTime = System.currentTimeMillis();
        LogUtil.i(TAG, "解析耗时：" + (endTime - startTime) + "ms");

        //解析完成，插入数据库
        mLoadRepository.prepareInsertXmlData();
        mLoadRepository.insertMainIconAll(mMainIcons);
        mLoadRepository.insertNewsAll(mNewsArrayLists);
        mLoadRepository.insertNewsLinkAll(mNewsLinks);
        mLoadRepository.insertWebContentAll(mWebContents);
        mLoadRepository.insertMapFloorAll(mMapFloors);
        mLoadRepository.insertExhibitorAll(mExhibitors);
        mLoadRepository.insertIndustryDtlAll(mIndustryDtls);
        mLoadRepository.insertIndustryAll(mIndustries);
        mLoadRepository.insertFloorAll(mFloors);


    }

    private <T> void logList(ArrayList<T> list, String tag) {
        if (list.size() > 0) {
            LogUtil.i("ContentHandler", "解析完成！！！！！！！" + list.size() + ",,," + tag + " == ");//+ list.toString()
        }
    }


}
