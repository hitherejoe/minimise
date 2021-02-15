package co.joebirch.minimise.common_ui

import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredSizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilledButton(
    modifier: Modifier,
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier.alpha(if (enabled) 1f else 0.6f),
        colors = buttonColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        shape = RoundedCornerShape(10.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            modifier = Modifier.preferredSizeIn(minWidth = 220.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun onSecondaryInputField(
    modifier: Modifier,
    inputValue: String,
    inputChanged: (value: String) -> Unit,
    label: String,
    keyboardType: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = inputValue,
        onValueChange = { value ->
            inputChanged(value)
        },
        label = {
            Text(
                text = label,
                fontSize = 12.sp
            )
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardType,
        keyboardActions = keyboardActions,
        modifier = modifier.fillMaxWidth(),
        inactiveColor = MaterialTheme.colors.onSecondary,
        activeColor = MaterialTheme.colors.onSecondary,
        textStyle = TextStyle(color = MaterialTheme.colors.onSecondary),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface,
    )
}