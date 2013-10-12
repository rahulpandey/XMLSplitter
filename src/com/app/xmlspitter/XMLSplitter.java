package com.app.xmlspitter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.cmu.relativelayout.Binding;
import edu.cmu.relativelayout.BindingFactory;
import edu.cmu.relativelayout.RelativeConstraints;
import edu.cmu.relativelayout.RelativeLayout;

public class XMLSplitter extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton sourceFileButton;
	private JButton destinationFileButton;
	private JButton splitterButton;
	private JList<String> jlist;
	private JTextField xmlTagText;
	private JLabel sourceFileLabel;
	private JLabel destinationFileLabel;
	public DefaultListModel<String> listModel;
	private File file;
	private File outPutDirectory;
	private JFileChooser chooser;

	public XMLSplitter() {
		super("XML File Splitter");
		init();
	}

	private void init() {
		listModel = new DefaultListModel<>();

		JPanel panel = new JPanel(new RelativeLayout());

		Font font1 = new Font("SansSerif", Font.PLAIN, 14);
		Font font2 = new Font("SansSerif", Font.BOLD, 14);
		sourceFileButton = new JButton(" Select ");
		destinationFileButton = new JButton("Save in");

		sourceFileLabel = new JLabel("Source file");
		destinationFileLabel = new JLabel("Destination file");
		sourceFileLabel.setFont(font1);
		destinationFileLabel.setFont(font1);
		JLabel convertLable = new JLabel("XML TAG");
		convertLable.setFont(font2);
		xmlTagText = new JTextField(20);
		xmlTagText.setFont(font1);

		Border paddingBorder = BorderFactory.createEmptyBorder(0,5,0,0);

		final Border createEtchedBorder = BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED);
		final CompoundBorder createCompoundBorder = BorderFactory.createCompoundBorder(createEtchedBorder,paddingBorder);
		sourceFileLabel.setBorder(createCompoundBorder);
		destinationFileLabel.setBorder(createCompoundBorder);
		convertLable.setBorder(createCompoundBorder);
		
		jlist = new JList<String>(); // data has type Object[]
		jlist.setModel(listModel);
		jlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jlist.setLayoutOrientation(JList.VERTICAL);
		jlist.setVisibleRowCount(-1);
		jlist.setFont(font1);
		jlist.setCellRenderer(new CellRenderer());

		splitterButton = new JButton("Split Tag Into Multiple XML File");

		JScrollPane scrollPane = new JScrollPane(jlist);

		BindingFactory bf = new BindingFactory();
		bf.setVerticalSpacing(10);
		bf.setHorizontalSpacing(10);
		Binding rightEdge = bf.rightEdge();
		Binding leftEdge = bf.leftEdge();
		Binding topEdge = bf.topEdge();

		Binding rightOfSourceFileButton = bf.rightOf(sourceFileButton);
		Binding bottomSourceEdge = bf
				.verticallyCenterAlignedWith(sourceFileButton);

		Binding bttomOfSourceButton = bf.below(sourceFileButton);
		Binding rightOfDestinationFileButton = bf
				.rightOf(destinationFileButton);
		Binding topDestinationEdge = bf
				.verticallyCenterAlignedWith(destinationFileButton);

		Binding bttomOfDestButton = bf.below(destinationFileButton);
		Binding alighCenterWithLable = bf
				.horizontallyCenterAlignedWith(destinationFileButton);
		Binding rightOfXmlLableField = bf.rightOf(convertLable);
		Binding topXMLTefirlfEdge = bf
				.verticallyCenterAlignedWith(convertLable);

		Binding bttomOfXMLTagField = bf.below(xmlTagText);
		Binding alighRightXMLTagField = bf.rightAlignedWith(xmlTagText);

		Binding bottomEdge = bf.bottomEdge();
		Binding bttomOfCovertField = bf.below(splitterButton);
		Binding alignRightWithSplitter = bf.rightAlignedWith(splitterButton);

		RelativeConstraints sourcePane = new RelativeConstraints(leftEdge,
				topEdge);
		RelativeConstraints lableSourcePane = new RelativeConstraints(topEdge,
				rightEdge, bottomSourceEdge, rightOfSourceFileButton);

		RelativeConstraints destinationPane = new RelativeConstraints(leftEdge,
				bttomOfSourceButton);
		RelativeConstraints lableDestinationPane = new RelativeConstraints(
				topDestinationEdge, rightEdge, rightOfDestinationFileButton);

		RelativeConstraints covertLablePane = new RelativeConstraints(leftEdge,
				alighCenterWithLable, bttomOfDestButton);
		RelativeConstraints covertTextFiledPane = new RelativeConstraints(
				topXMLTefirlfEdge, rightEdge, rightOfXmlLableField);

		RelativeConstraints convertButonpane = new RelativeConstraints(
				leftEdge, bttomOfXMLTagField, alighRightXMLTagField);

		RelativeConstraints jListScrollPane = new RelativeConstraints(leftEdge,
				bttomOfCovertField, alignRightWithSplitter, bottomEdge);

		panel.add(sourceFileButton, sourcePane);
		panel.add(sourceFileLabel, lableSourcePane);

		panel.add(destinationFileButton, destinationPane);
		panel.add(destinationFileLabel, lableDestinationPane);

		panel.add(convertLable, covertLablePane);
		panel.add(xmlTagText, covertTextFiledPane);

		panel.add(splitterButton, convertButonpane);
		panel.add(scrollPane, jListScrollPane);

		sourceFileButton.addActionListener(this);
		destinationFileButton.addActionListener(this);
		splitterButton.addActionListener(this);

		add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
				"xml files (*.xml)", "xml");
		if (chooser == null) {
			chooser = new JFileChooser();
			chooser.setFileFilter(xmlfilter);
		}
		if (event.getSource() == sourceFileButton) {
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setDialogTitle("Open xml file");
			chooser.setMultiSelectionEnabled(false);
			int option = chooser.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				file = new File(chooser.getSelectedFile().toString());
				sourceFileLabel.setText("Selected File=> "
						+ file.getAbsolutePath());
			}
		}
		if (event.getSource() == destinationFileButton) {

			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setDialogTitle("Select Directory");
			int option = chooser.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				outPutDirectory = new File(chooser.getSelectedFile().toURI());
				destinationFileLabel.setText("File Will Be Save in=> "
						+ outPutDirectory.getAbsolutePath());
			}
		}
		if (event.getSource() == splitterButton) {

			String FILE_TAG = xmlTagText.getText().toString().trim();
			if (file == null) {
				JOptionPane.showMessageDialog(this,
						"Please Select XML File That You want to Split");
				return;
			}
			if (outPutDirectory == null) {
				JOptionPane.showMessageDialog(this,
						"Please Select Destination to save file ");
				return;
			}
			if (FILE_TAG.equals("")) {
				JOptionPane.showMessageDialog(this, "Please Select Tag Name");
				return;
			}
			spilXMLSingleFileIntoMultipleFileBasedOnTag(FILE_TAG);

		}
	}

	private void spilXMLSingleFileIntoMultipleFileBasedOnTag(String FILE_TAG) {
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();

			if (!(outPutDirectory.isDirectory()))
				outPutDirectory.mkdir();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(new Date());
			String filename = FILE_TAG + "_" + timeStamp + "__";
			String finalDirPath = outPutDirectory + "/" + filename;

			NodeList list = doc.getElementsByTagName(FILE_TAG);
			for (int i = 0; i < list.getLength(); i++) {
				Node element = list.item(i).cloneNode(true);
				if (element.hasChildNodes()) {
					Source src = new DOMSource(element);
					FileOutputStream fs = new FileOutputStream(String.format(
							"%s%d.xml", finalDirPath, (i + 1)));
					Result dest = new StreamResult(fs);
					aTransformer.transform(src, dest);
					fs.close();
					listModel.addElement(String.format("%s%d.xml written",
							filename, (i + 1)));
					jlist.invalidate();
				}
			}

		} catch (TransformerException | IOException
				| ParserConfigurationException | SAXException e) {
			JOptionPane.showMessageDialog(this, e.getMessage().toString());

		}
	}

	public class CellRenderer extends JLabel implements
			ListCellRenderer<String> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

		public CellRenderer() {
			setOpaque(true);
			Font font1 = new Font("SansSerif", Font.PLAIN, 14);
			Border paddingBorder = BorderFactory.createEmptyBorder(2,5,2,5);
			final MatteBorder createMatteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
			setBorder(BorderFactory.createCompoundBorder(createMatteBorder, paddingBorder));
			setFont(font1);

		}

		public Component getListCellRendererComponent(
				JList<? extends String> list, String value, int index,
				boolean isSelected, boolean cellHasFocus) {

			setText(value);

			if (isSelected) {
				setBackground(HIGHLIGHT_COLOR);
				setForeground(Color.WHITE);
			} else {
				setBackground(Color.WHITE);
				setForeground(Color.BLACK);
			}
			return this;
		}

	}

}
