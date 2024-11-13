package com.example.shoppingcartapp
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingcartapp.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val totalCost = intent.getDoubleExtra("totalCost", 0.0)
        binding.totalAmountTextView.text = "Итого: $totalCost руб."

        binding.orderButton.setOnClickListener {
            val address = binding.addressEditText.text.toString()
            if (address.isNotEmpty()) {
                Toast.makeText(this, "Заказ оформлен на адрес: $address", Toast.LENGTH_SHORT).show()
                finish() // Закрыть активность после оформления заказа
            } else {
                Toast.makeText(this, "Пожалуйста, введите адрес", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
