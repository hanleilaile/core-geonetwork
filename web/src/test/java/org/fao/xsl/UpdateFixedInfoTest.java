package org.fao.xsl;

import static org.junit.Assert.assertTrue;
import jeeves.utils.Xml;

import org.fao.xsl.support.Count;
import org.fao.xsl.support.EqualText;
import org.fao.xsl.support.EqualTrimText;
import org.fao.xsl.support.Finder;
import org.fao.xsl.support.Requirement;
import org.jdom.Element;
import org.junit.Test;

public class UpdateFixedInfoTest {
	
	@Test
	public void convertLocales() throws Exception {
        String pathToXsl = TransformationTestSupport.geonetworkWebapp+"/WEB-INF/data/config/schema_plugins/iso19139.che/update-fixed-info.xsl";
		Element testData = Xml.loadString(
				"<che:CHE_MD_Metadata xmlns:che=\"http://www.geocat.ch/2008/che\" xmlns:gmd=\"http://www.isotc211.org/2005/gmd\" xmlns:gco=\"http://www.isotc211.org/2005/gco\" xmlns:gml=\"http://www.opengis.net/gml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" gco:isoType=\"gmd:MD_Metadata\">" +
				"   <gmd:identificationInfo>" +
				"     <che:CHE_MD_DataIdentification gco:isoType=\"gmd:MD_DataIdentification\">" +
				"       <gmd:topicCategory><gmd:MD_TopicCategoryCode> elevation</gmd:MD_TopicCategoryCode></gmd:topicCategory>" +
				"       <gmd:topicCategory><gmd:MD_TopicCategoryCode>  environment </gmd:MD_TopicCategoryCode></gmd:topicCategory>" +
				"       <gmd:topicCategory><gmd:MD_TopicCategoryCode> geoscientificInformation_Geology </gmd:MD_TopicCategoryCode></gmd:topicCategory>" +
				"     </che:CHE_MD_DataIdentification>" +
				"   </gmd:identificationInfo>" +
				"</che:CHE_MD_Metadata>", false);
		Element transformed = Xml.transform(testData, pathToXsl);
		findAndAssert(transformed, new Count(0, new Finder("topicCategory/MD_TopicCategoryCode", new EqualTrimText("environment"))));
		findAndAssert(transformed, new Count(1, new Finder("topicCategory/MD_TopicCategoryCode", new EqualTrimText("geoscientificInformation_Geology"))));
		findAndAssert(transformed, new Count(1, new Finder("topicCategory/MD_TopicCategoryCode", new EqualTrimText("geoscientificInformation"))));
		findAndAssert(transformed, new Count(1, new Finder("topicCategory/MD_TopicCategoryCode", new EqualTrimText("elevation"))));
	}
    private void findAndAssert(Element transformed, Requirement finder) {
		assertTrue(finder+" did not find a match in: \n"+Xml.getString(transformed), finder.eval(transformed));
	}

}