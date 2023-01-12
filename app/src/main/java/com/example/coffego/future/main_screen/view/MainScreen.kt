package com.example.coffego.future.main_screen.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.coffego.R
import com.example.coffego.future.common.LoadingWidget
import com.example.coffego.future.debug.all_logins.view.ConErr
import com.example.coffego.future.main_screen.model.MainScreenState
import com.example.coffego.future.main_screen.view_model.MainScreenViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel
) {
    val state by remember {
        mutableStateOf(viewModel.state)
    }
    when (state.value) {
        MainScreenState.ConnectionError -> ConErr {
            viewModel.getCatalog()
        }
        MainScreenState.Loading -> LoadingWidget()
        is MainScreenState.Success -> Content(
            viewModel = viewModel,
            data = (state.value as MainScreenState.Success)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    viewModel: MainScreenViewModel,
    data: MainScreenState.Success
) {
    val pagerState = rememberPagerState(0, 0f)
    Column(modifier = Modifier.fillMaxSize()) {
        AppBar()
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp)),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Tab(
                selected = pagerState.currentPage == 0,
                text = { Text("Чай") },
                onClick = {},
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Gray.copy(0.3f))
                    .zIndex(10000f),
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White
            )
            Tab(
                selected = pagerState.currentPage == 1,
                text = { Text("Кофе") },
                onClick = {},
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Gray.copy(0.3f))
                    .zIndex(10000f),
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White
            )
        }
        HorizontalPager(pageCount = 2, state = pagerState) {
            if (it == 0) A() else B()
        }
    }
}

@Composable
fun A() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "1",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Composable
fun B() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "2",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Composable
fun AppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
        )
        Text(
            "Каталог", style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                modifier = Modifier.size(70.dp),
                onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.cart),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
            }
            Box(
                modifier = Modifier
                    .offset(3.dp, 3.dp)
                    .size(20.dp)
                    .background(MaterialTheme.colorScheme.onPrimary, shape = CircleShape)
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "2",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}