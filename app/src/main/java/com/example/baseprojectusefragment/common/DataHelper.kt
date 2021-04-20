package com.example.baseprojectusefragment.common

import android.content.Context
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.ui.model.Contact
import ezvcard.Ezvcard
import io.reactivex.Observable
import java.lang.Exception
import kotlin.random.Random

object DataHelper {

    fun getContactList(context: Context): Observable<List<Contact>> {
        try {
            val contactList = arrayListOf<Contact>()
            val vcards = Ezvcard.parse(context.assets.open("1000contacts-examples.vcf")).all()
            for (vcard in vcards) {
                val name = vcard.formattedName.value
                var number = ""
                for (tel in vcard.telephoneNumbers) {
                    number = tel.text
                }
                val random = Random(10)
                val photoRes = if (random.nextInt() % 3 == 0) R.drawable.ic_baseline_account_box_24 else R.drawable.ic_baseline_account_circle_24
                val contact = Contact(name, number, photoRes)
                contactList.add(contact)
            }
            return Observable.fromArray(contactList)
        } catch (ex: Exception) {
            return Observable.empty()
        }
    }
}