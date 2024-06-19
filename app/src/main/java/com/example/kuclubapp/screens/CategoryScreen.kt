import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kuclubapp.NavRoutes
import com.example.kuclubapp.R
import com.example.kuclubapp.viewmodel.NavUserViewModel

data class CategoryItem(val icon: Int, val title: String)

@Composable
fun CategoryScreen(navController: NavHostController, navUserViewModel: NavUserViewModel) {
    val categories = listOf(
        CategoryItem(R.drawable.ball, "구기체육"),
        CategoryItem(R.drawable.science, "자연과학"),
        CategoryItem(R.drawable.art, "공연예술"),
        CategoryItem(R.drawable.brush, "전시문예"),
        CategoryItem(R.drawable.imac, "개발/코딩"),
        CategoryItem(R.drawable.lifecycle, "봉사"),
        CategoryItem(R.drawable.battery, "더미 데이터"),
        CategoryItem(R.drawable.battery, "더미 데이터")
    )

    val cardSize = 150.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD9FDE8).copy(alpha = 0.6f))
            .padding(16.dp), // Padding from the edges
        verticalArrangement = Arrangement.spacedBy(16.dp), // Space between rows
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(categories.chunked(2)) { _, rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { item ->
                    CategoryCard(item, cardSize) {
                        navController.navigate(NavRoutes.CategoryClubList.route)
                    }
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun CategoryCard(item: CategoryItem, cardSize: Dp, onClick: (CategoryItem) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
            .size(cardSize)
            .clickable { onClick(item) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter: Painter = painterResource(id = item.icon)
            Image(
                painter = painter,
                contentDescription = item.title,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = item.title, fontSize = 14.sp)
        }
    }
}