package com.kjfmbktgl4.fintrack.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kjfmbktgl4.fintrack.R;
import com.kjfmbktgl4.fintrack.data.DatabaseHandler;
import com.kjfmbktgl4.fintrack.model.TransactionItem;
import com.kjfmbktgl4.fintrack.util.DateConverters;
import com.kjfmbktgl4.fintrack.util.Preferences;
import com.kjfmbktgl4.fintrack.util.Util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditTransactionFragment extends Fragment implements View.OnClickListener, ChipGroup.OnCheckedChangeListener {

	Button saveButton;
	Button cancelButton;
	Button product1, product2;
	ImageButton delButton;
	TextInputEditText dateET, amountET, noteET;
	Chip categoryChip, accountChip, selChipCategory, selChipAccount;
	ChipGroup categoryChipGroup, accountChipGroup;
	Boolean error = false;
	ImageButton delIV;
	DatabaseHandler db;
	TemplateView template;
	String categoryName, accountName;
	TextInputLayout amountTIL;
	public boolean isNew = false;
	int id;
	/*BillingFlowParams billingFlowParamsOne, billingFlowParamsTwo;
	BillingClient billingClient;*/



	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

/*
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);
*/


		View v = inflater.inflate(R.layout.add_edit_trans_fragment, container, false);

		amountET = v.findViewById(R.id.editTextAmount);
		noteET = v.findViewById(R.id.editTextNote);
		saveButton = v.findViewById(R.id.buttonsave);
		cancelButton = v.findViewById(R.id.buttoncancel);
		dateET = v.findViewById(R.id.editTextDate);
		delIV = v.findViewById(R.id.imageButton_delDF);
		amountTIL = v.findViewById(R.id.amountTIL);
		categoryChipGroup = v.findViewById(R.id.catChipGroup);
		accountChipGroup = v.findViewById(R.id.actChipGroup);
		selChipCategory = v.findViewById(categoryChipGroup.getCheckedChipId());
		selChipAccount = v.findViewById(accountChipGroup.getCheckedChipId());
		template = v.findViewById(R.id.ad_small);
	/*	product1 = v.findViewById(R.id.button1);
		product2 = v.findViewById(R.id.button2);*/

		categoryChipGroup.setOnCheckedChangeListener(this);
		accountChipGroup.setOnCheckedChangeListener(this);

		return v;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		dateET.setOnClickListener(this);
		delIV.setOnClickListener(this);
/*		product1.setOnClickListener(this);
		product2.setOnClickListener(this);*/

		db = new DatabaseHandler(getActivity());
		isNew = EditTransactionFragmentArgs.fromBundle(getArguments()).getIsNew();
		id = EditTransactionFragmentArgs.fromBundle(getArguments()).getIdTransaction();

		showAd();
		/*setUpBillingClient();*/

/*
		Bundle bundle = getArguments();
		isNew = bundle.getBoolean("isNew", false);
		id = bundle.getInt("id", 0);
*/


		createCategoryViews();

		if (!isNew) {
			setValuesToEdit();
		} else {
			setCurrentDate();
			setDefaultSelection();
		}
		setCurrency();
	}


	/*private void setUpBillingClient() {
*//*	// OnCreate calls set up billing client, billing client has listener and calls loadAllSkU after start connection
		//loadallsku has querySKUDetails which gets detaills, set click, launches billing flow
		// onpurchaseupdated handles error codes and calls acknowledge purchases
		// acknowledge purchase has acknowledge purchases*//*

		billingClient = BillingClient.newBuilder(getActivity())
				.setListener(purchaseUpdateListener)
				.enablePendingPurchases()
				.build();


		billingClient.startConnection(new BillingClientStateListener() {
			@Override
			public void onBillingSetupFinished(BillingResult billingResult) {
				Log.d(Util.TAG,billingResult.getResponseCode()+ "here");
				if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
					// The BillingClient is ready. You can query purchases here.
					loadAllSKU();
				}
			}

			@Override
			public void onBillingServiceDisconnected() {
				// Try to restart the connection on the next request to
				// Google Play by calling the startConnection() method.
			}
		});


	}
*/
/*
	private void loadAllSKU() {

		List<String> skuList = new ArrayList<>();
		skuList.add("ring");
		skuList.add("test_product_trwo");

		SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
		params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);

		billingClient.querySkuDetailsAsync(params.build(),
				new SkuDetailsResponseListener() {
					@Override
					public void onSkuDetailsResponse(BillingResult billingResult,
					                                 List<SkuDetails> skuDetailsList) {
						// Process the result.
						Log.d(Util.TAG, (billingResult.getResponseCode()== BillingClient.BillingResponseCode.OK)+" response code");
						Log.d(Util.TAG, skuDetailsList.size()+" size");

						if (!skuDetailsList.isEmpty()) {
							for (SkuDetails lSkuDetails : skuDetailsList) {
								// Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
								if (lSkuDetails.getSku() == "test_product_one") {
									BillingFlowParams billingFlowParamsOne = BillingFlowParams.newBuilder()
											.setSkuDetails(lSkuDetails)
											.build();
								}
							}
						}


					}
				});
		// Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().

	}

	private PurchasesUpdatedListener purchaseUpdateListener = new PurchasesUpdatedListener() {
		@Override
		public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
			// To be implemented in a later section.
		}
	};

	private void launchPurchaseOne() {
		billingClient.launchBillingFlow(getActivity(), billingFlowParamsOne);
		int responseCode = billingClient.launchBillingFlow(getActivity(), billingFlowParamsOne).getResponseCode();
// Handle the result.

	}

	private void launchPurchaseTwo() {
	}
*/

	private void showAd() {

		AdLoader.Builder builder = new AdLoader.Builder(
				getContext(), getResources().getString(R.string.adUnit_ID));

		builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
			@Override
			public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
				ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.amber_500));


				NativeTemplateStyle styles = new
						NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

				template.setNativeAd(unifiedNativeAd);
				template.setStyles(styles);
			}
		});

		AdLoader adLoader = builder.build();
		adLoader.loadAd(new AdRequest.Builder().build());


	}

	private void setDefaultSelection() {

		Chip chip = (Chip) categoryChipGroup.getChildAt(0);
		chip.setChecked(true);
		categoryChipGroup.setSingleSelection(true);


		Chip chipAct = (Chip) accountChipGroup.getChildAt(0);
		chipAct.setChecked(true);
		accountChipGroup.setSingleSelection(true);


	}

	private void setValuesToEdit() {
		TransactionItem transactionItem = new TransactionItem();
		transactionItem = db.getOneTransaction(id);

		amountET.setText((String.valueOf(transactionItem.getAmountOfTransaction())));
		noteET.setText(transactionItem.getNoteOfTransaction());

		Date transactionDate = new Date(transactionItem.getDateOfTransaction());
		String tranDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(transactionDate);
		dateET.setText(tranDate);


		categoryName = transactionItem.getNameCategoryOfTransaction();
		accountName = transactionItem.getAccountOfTransaction();


		for (int i = 0; i < categoryChipGroup.getChildCount(); i++) {
			Chip chip = (Chip) categoryChipGroup.getChildAt(i);
			if (categoryName.equals(chip.getText())) {
				chip.setChecked(true);
			}
		}
		categoryChipGroup.setSingleSelection(true);


		for (int i = 0; i < accountChipGroup.getChildCount(); i++) {
			Chip chip = (Chip) accountChipGroup.getChildAt(i);
			if (accountName.equals(chip.getText())) {
				chip.setChecked(true);
			}
		}
		accountChipGroup.setSingleSelection(true);
	}

	private void setCurrency() {
		Locale curLocale = Locale.getDefault();
		Currency curr = Currency.getInstance(curLocale);
		String symbol = curr.getSymbol();
		amountTIL.setPrefixText(symbol);
	}

	private void setCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
		dateET.setText(currentDate);
	}

	private void createCategoryViews() {
		List<String> mcategoryName;
		mcategoryName = Preferences.getArrayPrefs("CategoryNames", getContext());
		Log.d(Util.TAG, "Category Names " + mcategoryName.size());


		for (String category : mcategoryName) {
			Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, categoryChipGroup, false);
			chip.setText(category);
			categoryChipGroup.addView(chip);
			categoryChipGroup.setSingleSelection(true);
			categoryChipGroup.setSelectionRequired(true);
		}

		List<String> accountNames;
		accountNames = Preferences.getArrayPrefs("AccountNames", getContext());

		for (String accountName : accountNames) {
			Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, accountChipGroup, false);
			chip.setText(accountName);
			accountChipGroup.addView(chip);
			accountChipGroup.setSingleSelection(true);
			accountChipGroup.setSelectionRequired(true);
		}

	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.buttonsave:
				if (isNew) {
					saveNewTransacton();
				} else {
					updateTransaction();
				}
				if (!error) {
					startPrevAct();
				}
				break;
			case R.id.buttoncancel:
				startPrevAct();
				break;

			case R.id.editTextDate:
				//case R.id.dateTIL:
				pickerShow();
				break;
			case R.id.imageButton_delDF:
				deleteAlertDialog();
				break;

	/*		case R.id.button1:
				launchPurchaseOne();
				break;

			case R.id.button2:
				launchPurchaseTwo();
				break;*/
		}

	}


	private void startPrevAct() {
		Intent intent = new Intent(getContext(), MainActivity.class);
		startActivity(intent);
	}

	private void updateTransaction() {
		checkForErrors();
		if (!error) {
			TransactionItem transaction = new TransactionItem();
			//transaction.setNameCategoryOfTransaction("Food");
			transaction.setNoteOfTransaction(String.valueOf(noteET.getText()));
			transaction.setAmountOfTransaction(Long.parseLong(String.valueOf(amountET.getText())));
			transaction.setId(id);
			transaction.setDateOfTransaction(DateConverters.dateStringToLong(dateET.getText().toString()));

			if (categoryChipGroup.getCheckedChipId() == -1) {
				transaction.setNameCategoryOfTransaction("Others");
			} else {
				int id = categoryChipGroup.getCheckedChipId();
				//selChipCategory =(Chip)categoryChipGroup.getChildAt(id-1-(categoryChipGroup.getChildCount()));
				transaction.setNameCategoryOfTransaction((selChipCategory.getText().toString()));

				/*//Chip selChipCategory = v.findViewById(categoryChipGroup.getCheckedChipId());
				String selChipString = String.valueOf(selChipCategory.getText());
				transaction.setNameCategoryOfTransaction(selChipString);*/
			}


			if (accountChipGroup.getCheckedChipId() == -1) {
				transaction.setAccountOfTransaction("Others");
			} else {
				int id1 = accountChipGroup.getCheckedChipId();
				//selChipAccount =(Chip)accountChipGroup.getChildAt(id1-1);
				transaction.setAccountOfTransaction((selChipAccount.getText().toString()));

				//Chip selChipAccount = v.findViewById(accountChipGroup.getCheckedChipId());
				//String selChipStringAccount = String.valueOf(selChipAccount.getText());
				//transaction.setAccountOfTransaction(selChipStringAccount);
			}

			db.updateTransaction(transaction);
		}
	}

	public void pickerShow() {
		final Calendar cldr = Calendar.getInstance();
		int day = cldr.get(Calendar.DAY_OF_MONTH);
		int month = cldr.get(Calendar.MONTH);
		int year = cldr.get(Calendar.YEAR);
		// date picker dialog
		DatePickerDialog picker = new DatePickerDialog(getContext(),
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						cldr.set(year, monthOfYear, dayOfMonth);
						String setDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(cldr.getTime());

						dateET.setText(setDate);
					}
				}, year, month, day);
		picker.show();
	}

	private void saveNewTransacton() {
		checkForErrors();
		if (!error) {
			TransactionItem transaction = new TransactionItem();
			int id = categoryChipGroup.getCheckedChipId();
			if (id != -1) {
				//selChipCategory =(Chip)categoryChipGroup.getChildAt(id-1);

				transaction.setNameCategoryOfTransaction((selChipCategory.getText().toString()));
			} else transaction.setNameCategoryOfTransaction("Others");

			transaction.setNoteOfTransaction(String.valueOf(noteET.getText()));
			transaction.setAmountOfTransaction(Long.parseLong(String.valueOf(amountET.getText())));
			transaction.setDateOfTransaction(DateConverters.dateStringToLong(dateET.getText().toString()));
			int idAccount = accountChipGroup.getCheckedChipId();
			if (idAccount != -1) {
				//	selChipAccount =(Chip)accountChipGroup.getChildAt(id-1);
				transaction.setAccountOfTransaction((selChipAccount.getText().toString()));
			} else transaction.setAccountOfTransaction("Others");
			db.addTransaction(transaction);
			db.close();
		}


	}

	private void checkForErrors() {

		if (TextUtils.isEmpty(amountET.getText())) {
			amountTIL.setError("Please enter an amount");
			error = true;
		} else {
			error = false;
		}


	}

	private void deleteEntry() {
		Log.d(Util.TAG, "delete " + id);
		db.deleteOne(id);

	}

	private void deleteAlertDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
		alertDialogBuilder.setMessage("Are you sure you want to delete this entry?");
		alertDialogBuilder.setPositiveButton("yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						deleteEntry();
						startPrevAct();
					}
				});

		alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/*finish();*/
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	@Override
	public void onCheckedChanged(ChipGroup pChipGroup, int pI) {

		if (pChipGroup.getId() == (categoryChipGroup.getId())) {
			selChipCategory = getView().findViewById(pI);
			Log.d(Util.TAG, "Here in chip checked change cat" + selChipCategory.getText().toString());
		} else {
			selChipAccount = getView().findViewById(pI);
			Log.d(Util.TAG, "Here in chip checked change act" + selChipAccount.getText().toString());
		}

		/*
		Log.d(Util.TAG,"Here in chip checked change act"+selChipAccount.getText());*/

	}
}
