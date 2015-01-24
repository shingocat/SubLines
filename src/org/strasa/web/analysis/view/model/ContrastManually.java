package org.strasa.web.analysis.view.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.filesystem.manager.UserFileManager;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

public class ContrastManually {
//	component
	Doublespinner contrastDS;
	Button saveBtn;
	Div gridDiv;
	Grid  grid;
	Columns columns;
	Rows rows;
	
	List<String[]> dataList;
	
	String filename = null;
	File file = null;
	File tempFile = null;
	String factor = null;
	String levelName = null;
	String uploadedFileFolderPath = null;
	List<String> levels = null;
	UserFileManager userFileManager = null;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("Arguments") HashMap<String, Object> args)
	{
		Selectors.wireComponents(component, this, false);
		if(args.containsKey("LevelName"))
			levelName = (String) args.get("LevelName");
		if(args.containsKey("UploadedFileFolderPath"))
			uploadedFileFolderPath = (String) args.get("UploadedFileFolderPath");
		if(args.containsKey("Levels"))
			levels = (List<String>) args.get("Levels");
		if(args.containsKey("Factor"))
			factor = (String) args.get("Factor");
		contrastDS = (Doublespinner) component.getFellow("contrastDS");
		saveBtn = (Button) component.getFellow("saveBtn");
		gridDiv = (Div) component.getFellow("gridDiv");
		grid = (Grid) component.getFellow("grid");
		initGrid();
	}
	
	@NotifyChange("*")
	@Command("save")
	public void save()
	{
		if(!validateGrid())
			return;
		if(dataList == null)
			dataList = new ArrayList<String[]>();
		dataList.clear();
//		header values
		String[] headers = new String[1 + columns.getChildren().size()];
		for(int i = 0; i < columns.getChildren().size(); i ++)
		{
			headers[i] = ((Column)columns.getChildren().get(i)).getLabel();
		}
		dataList.add(headers);
//		data value;
		for(int i = 0; i < rows.getChildren().size(); i++)
		{
			String[] values = new String[1 + columns.getChildren().size()];
			Row row = (Row) rows.getChildren().get(i);
			for(int j = 0; j < row.getChildren().size(); j++)
			{
				if(j == 0)
				{
					Textbox input = (Textbox)row.getChildren().get(j);
					input.setDisabled(true);
					values[j] = input.getValue();
				}
				else
				{
					Doublebox db = (Doublebox)row.getChildren().get(j);
					db.setDisabled(true);
					values[j] = String.valueOf(db.getValue());
				}
			}
			dataList.add(values);
		}
		String fileName = factor + "_Manually_" + levelName + "_(Contrast).csv";
		FileUtilities.saveDataToFile(dataList, fileName, uploadedFileFolderPath);
		String filePath = uploadedFileFolderPath + File.separator + fileName;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("Name", levelName);
		args.put("FilePath", filePath);
		args.put("Factor", factor);
		BindUtils.postGlobalCommand(null, null, "getUploadedContrastFiles", args);
		saveBtn.setDisabled(true);
		contrastDS.setDisabled(true);
	}
	
	@NotifyChange("*")
	@Command("update")
	public void update(@BindingParam("inputValue")Integer value)
	{
		int numRows = rows.getChildren().size();
		if(value > numRows)
		{
			Row row = new Row();
			Textbox label = new Textbox();
			label.setHflex("1");
			label.setValue("C" + value);
			label.setVisible(true);
			label.setConstraint("no empty");
			label.setParent(row);
			
			for(int i = 0; i < levels.size(); i++)
			{
				Doublebox db = new Doublebox();
				db.setHflex("1");
				db.setVisible(true);
				db.setConstraint("no empty");
				db.setParent(row);
			}
			row.setParent(rows);
			saveBtn.setDisabled(false);
		} else 
		{
			rows.removeChild(rows.getLastChild());
			if(numRows == 0)
				saveBtn.setDisabled(true);
		}
	}
	
	public boolean validateGrid()
	{
		if(rows.getChildren().isEmpty())
		{
			showMessage("Please input the contrast manually!");
			return false;
		} else
		{
			for(int i = 0 ; i < rows.getChildren().size(); i++)
			{
				Row row = (Row) rows.getChildren().get(i);
				String [] values = new String[row.getChildren().size()];
				for(int j = 0; j < row.getChildren().size(); j++)
				{
					if(j == 0)
					{
						Textbox input = (Textbox) row.getChildren().get(j);
						try{
							if(input.isValid()){
								values[j] = input.getValue();
							} else
							{
								showMessage("Please input contrast name on row " + (i + 1) + 
										" and column " + (j + 1) + " of " + levelName + ".");
								return false;
							}
						}catch(WrongValueException e)
						{
							showMessage("Please input valid contrast name on row " + (i + 1) + 
									" and column " + (j + 1) + " of " + levelName + ".");
							e.printStackTrace();
							return false;
						}
					} else
					{
						Doublebox db = (Doublebox) row.getChildren().get(j);
						try{
							if(db.isValid())
							{
								values[j] = String.valueOf(db.getValue());
							} else
							{
								showMessage("Please input contrast value on row " + (i + 1) + 
										" and column " + (j + 1) + " of " + levelName + ".");
								return false;
							}
						} catch(WrongValueException e)
						{
							showMessage("Please input valid contrast value on row " + (i + 1) + 
									" and column " + (j + 1) + " of " + levelName + ".");
							e.printStackTrace();
							return false;
						}
					}
				}

				double sum = 0;
				for(int k = 1; k < values.length; k++)
				{
					sum = sum + Double.valueOf(values[k]);
				}
				if(sum != 0)
				{
					showMessage("Please check contrast coefficients on " + (i+1) +  " rows of " + levelName +
							". It should be sum to zero!");
					return false;
				}
			}
		}
		return true;
	}
	
	public void initGrid()
	{
		columns = new Columns();
		Column column = new Column();
		column.setLabel("Label");
		column.setHflex("1");
		columns.appendChild(column);
		for(String s : levels)
		{
			Column col = new Column();
			col.setLabel(s);
			col.setHflex("1");
			columns.appendChild(col);
		}
		
		rows = new Rows();
		
		grid.appendChild(columns);
		grid.appendChild(rows);
	}
	
	public void showMessage(String message)
	{
		Messagebox.show(message, "Warnings", Messagebox.OK, Messagebox.INFORMATION);
	}
}
