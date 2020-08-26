package com.kjfmbktgl4.fintrack.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode.OK
import com.android.billingclient.api.BillingClient.BillingResponseCode.USER_CANCELED

class IAP : AppCompatActivity(), PurchasesUpdatedListener {
    val TAG: String ="IAP"
    private lateinit var billingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the billing client
        billingClient = BillingClient
                .newBuilder(this)
                .enablePendingPurchases()
                .setListener(this)
                .build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.i(TAG, "Billing client successfully set up")
                    queryOneTimeProducts()
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.i(TAG, "Billing service disconnected")
            }
        })
    }

    private fun queryOneTimeProducts() {
        val skuListToQuery = ArrayList<String>()
        skuListToQuery.add("test_product_one")
        skuListToQuery.add("coin")
        // ‘coins_5’ is the product ID that was set in the Play Console.
        // Here is where we can add more product IDs to query for based on
        //   what was set up in the Play Console.

        val params = SkuDetailsParams.newBuilder()
        params
                .setSkusList(skuListToQuery)
                .setType(BillingClient.SkuType.INAPP)
        // SkuType.INAPP refers to 'managed products' or one time purchases.
        // To query for subscription products, you would use SkuType.SUBS.

        billingClient.querySkuDetailsAsync(
                params.build(),
                object : SkuDetailsResponseListener {
                    override fun onSkuDetailsResponse(p0: BillingResult, skuDetails: MutableList<SkuDetails>?) {
                        Log.i(TAG, "onSkuDetailsResponse ")
                        if (skuDetails != null) {
                            for (skuDetail in skuDetails) {
                                Log.i(TAG, skuDetail.toString())
                            }
                        } else {
                            Log.i(TAG, "No skus found from query")
                        }
                    }
                })
    }

    // Google Play calls this method to propogate the result of the purchase flow
    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase?>?) {
        if (billingResult.responseCode == OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == USER_CANCELED) {
            Log.i(TAG, "User cancelled purchase flow.")
        } else {
            Log.i(TAG, "onPurchaseUpdated error: ${billingResult?.responseCode}")
        }
    }

    private fun handlePurchase(purchase: Purchase?) {

    }
}