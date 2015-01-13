package org.strasa.web.createfieldbook.view.pojos;

import java.io.File;

public interface CreateFieldBookThread {

	public void updateUI(String msg);

	public void onStart();

	public void onFinish(File outputFile);

	public void onError(String errorMsg);
}
