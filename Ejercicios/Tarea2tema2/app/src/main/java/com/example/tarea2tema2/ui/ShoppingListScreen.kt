package com.example.tarea2tema2.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tarea2tema2.R
import com.example.tarea2tema2.model.Product
import com.example.tarea2tema2.ui.theme.Tarea2Tema2Theme
import com.example.tarea2tema2.viewmodel.ShoppingListViewModel

@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingListViewModel = viewModel(),
) {
    val products by viewModel.products.collectAsState()

    ShoppingListContent(
        products = products,
        onAddProduct = { viewModel.addProduct(it) },
        onTogglePurchased = { viewModel.toggleProductPurchased(it) },
        onDeleteProduct = { viewModel.removeProduct(it) },
        modifier = modifier
    )
}

@Composable
fun ShoppingListContent(
    products: List<Product>,
    onAddProduct: (String) -> Unit,
    onTogglePurchased: (Product) -> Unit,
    onDeleteProduct: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    var newProductName by remember { mutableStateOf("") }
    val pendingCount = products.count { !it.isPurchased }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.margin_medium))
    ) {
        // Sección Superior: Contador de Pendientes
        Text(
            text = pluralStringResource(
                id = R.plurals.pending_products_count,
                count = pendingCount,
                pendingCount
            ),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.margin_small))
        )

        // Fila de Entrada (Row)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.margin_medium)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_small))
        ) {
            OutlinedTextField(
                value = newProductName,
                onValueChange = { newProductName = it },
                label = { Text(stringResource(id = R.string.product_name_hint)) },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (newProductName.isNotBlank()) {
                        onAddProduct(newProductName)
                        newProductName = ""
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.add_button))
            }
        }

        // Estado Vacío o Lista de Productos (LazyColumn)
        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.empty_list_message),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_small))
            ) {
                items(
                    items = products,
                    key = { it.id }
                ) { product ->
                    ProductCard(
                        product = product,
                        onTogglePurchased = { onTogglePurchased(product) },
                        onDeleteProduct = { onDeleteProduct(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onTogglePurchased: () -> Unit,
    onDeleteProduct: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTogglePurchased() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (product.isPurchased) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.margin_small))
        ) {
            // Box para posicionar el icono indicador de estado en la esquina de la tarjeta
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(dimensionResource(id = R.dimen.corner_padding))
            ) {
                IconButton(
                    onClick = onTogglePurchased
                ) {
                    val icon = if (product.isPurchased) {
                        Icons.Default.CheckCircle
                    } else {
                        Icons.Default.RadioButtonUnchecked
                    }
                    val description = if (product.isPurchased) {
                        stringResource(id = R.string.icon_purchased_description)
                    } else {
                        stringResource(id = R.string.icon_pending_description)
                    }
                    val tint = if (product.isPurchased) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = description,
                        tint = tint
                    )
                }
            }

            // Contenido principal: Nombre del producto y botón de eliminar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.icon_space),
                        end = dimensionResource(id = R.dimen.margin_small)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = if (product.isPurchased) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (product.isPurchased) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onDeleteProduct
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.icon_delete_description),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ShoppingListContentPreview() {
    Tarea2Tema2Theme {
        ShoppingListContent(
            products = listOf(
                Product(name = stringResource(id = R.string.preview_milk), isPurchased = false),
                Product(name = stringResource(id = R.string.preview_bread), isPurchased = true),
                Product(name = stringResource(id = R.string.preview_apples), isPurchased = false)
            ),
            onAddProduct = {},
            onTogglePurchased = {},
            onDeleteProduct = {}
        )
    }
}

@Preview(name = "Empty State", showBackground = true)
@Composable
fun ShoppingListEmptyPreview() {
    Tarea2Tema2Theme {
        ShoppingListContent(
            products = emptyList(),
            onAddProduct = {},
            onTogglePurchased = {},
            onDeleteProduct = {}
        )
    }
}
