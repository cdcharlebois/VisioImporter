package visioimporter.impl;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aspose.diagram.Diagram;
import com.aspose.diagram.Page;
import com.aspose.diagram.Shape;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class VisioExtractor {

	/**
	 * Given dimensions (Visio marks) of a region, returns a comma-delimited list of
	 * all the text in that region
	 * 
	 * @param page
	 * @param xMin
	 * @param xMax
	 * @param yMin
	 * @param yMax
	 * @return
	 */
	public static String getTextFromRegion(Page page, double xMin, double xMax, double yMin, double yMax) {
		String ret = "";
		for (Shape s : (Iterable<Shape>) page.getShapes()) {
			double x = s.getXForm().getPinX().getValue();
			double y = s.getXForm().getPinY().getValue();
			/**
			 * ECN Number X: [10, 14.5] Y: [20.25, 20.875]
			 */
			if (xMin < x && x < xMax && yMin < y && y < yMax) {
				String text = s.getPureText();
				ret += ret.isBlank() ? text : "," + text;

			}

		}
		return ret;
	}

	public static Diagram diagramFromFile(IMendixObject fileMendixObject, IContext ctx) throws Exception {
		// get inputStream from file
		InputStream inputStream = Core.getFileDocumentContent(ctx, fileMendixObject);
		return new Diagram(inputStream);
	}
	
}
