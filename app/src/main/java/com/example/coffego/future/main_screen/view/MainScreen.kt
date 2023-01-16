package com.example.coffego.future.main_screen.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.coffego.R
import com.example.coffego.data.network.catalog.Product
import com.example.coffego.future.common.LoadingWidget
import com.example.coffego.future.debug.all_logins.view.ConErr
import com.example.coffego.future.main_screen.model.MainScreenState
import com.example.coffego.future.main_screen.view_model.CartButtonViewModel
import com.example.coffego.future.main_screen.view_model.CartDialogViewModel
import com.example.coffego.future.main_screen.view_model.MainScreenViewModel
import kotlinx.coroutines.launch

private var currentProduct by mutableStateOf(
    Product(
        "", "", "", 0
    )
)
private var currentCategory by mutableStateOf("")
private var dialogVisible by mutableStateOf(false)

@ExperimentalAnimationApi
@ExperimentalGlideComposeApi
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
            data = (state.value as MainScreenState.Success).data
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalGlideComposeApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    data: Map<String, List<Product>>
) {
    val pagerState = rememberPagerState(0, 0f)
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    Column(modifier = Modifier
        .fillMaxSize()
        .clickable(
            interactionSource = interactionSource, indication = null
        ) {
            dialogVisible = false
        }) {
        AppBar()
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .padding(horizontal = 2.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp)),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier.padding(10.dp)
        ) {
            val listData = data.toList()
            repeat(listData.size) {
                Tab(
                    selected = pagerState.currentPage == it,
                    text = { Text(listData[it].first) },
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(0.3f))
                        .zIndex(10000f),
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White
                )
            }
        }
        Main(
            data, pagerState
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalGlideComposeApi
@ExperimentalFoundationApi
@Composable
fun Main(
    data: Map<String, List<Product>>, pagerState: PagerState
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            pageCount = data.size,
            state = pagerState,
            pageSpacing = 14.dp,
            contentPadding = PaddingValues(
                horizontal = 10.dp
            )
        ) {
            Screen(data = data.toList()[it].second, category = data.toList()[it].first)
        }
        CartDialog(false)
    }
}

@ExperimentalGlideComposeApi
@ExperimentalAnimationApi
@Composable
fun CartDialog(
    isVisible: Boolean,
    viewModel: CartDialogViewModel = hiltViewModel()
) {
    val state = remember {
        mutableStateOf(viewModel.state.value)
    }
    AnimatedContent(
        targetState = isVisible,
        modifier = Modifier
            .alpha(if (dialogVisible) 1f else 0f)
            .fillMaxWidth(0.7f)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White.copy(0.8f))
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                model = "https://server.krea-company.keenetic.pro/catalog/getProductIcon/$currentCategory/${currentProduct.icon_name}",
                contentDescription = currentProduct.icon_name
            )
            Text(
                currentProduct.item,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 46.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                currentProduct.item_description,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(modifier = Modifier
                    .height(50.dp)
                    .width(50.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                    //Text("-", textAlign = TextAlign.Center, fontSize = 30.sp, modifier = Modifier.align(Alignment.CenterVertically))
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.value.toString())
                }
                IconButton(modifier = Modifier
                    .height(50.dp)
                    .width(50.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = { viewModel.add(
                        currentProduct
                    ) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                    //Text("-", textAlign = TextAlign.Center, fontSize = 30.sp, modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@ExperimentalGlideComposeApi
@Composable
fun Screen(
    data: List<Product>, category: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            content = {
                items(data.size) { i ->
                    val item = data[i]
                    Box(modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            if (dialogVisible && currentProduct == item) {
                                dialogVisible = false
                                return@clickable
                            }
                            currentProduct = item
                            currentCategory = category
                            dialogVisible = true
                        }
                        .padding(10.dp), contentAlignment = Alignment.Center) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            GlideImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                contentScale = ContentScale.Fit,
                                model = "https://server.krea-company.keenetic.pro/catalog/getProductIcon/$category/${item.icon_name}",
                                contentDescription = item.item
                            )
                            Text(
                                item.item,
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                item.price.toString(),
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            })
    }
}

@ExperimentalAnimationApi
@Composable
fun AppBar(
    viewModel: CartButtonViewModel = hiltViewModel()
) {
    val count by remember {
        mutableStateOf(viewModel.state)
    }
    val countAnim by animateFloatAsState(targetValue = if (count.value == 0) 0f else 1f)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(44.dp)
        )
        Text(
            "Каталог",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            IconButton(modifier = Modifier.size(70.dp), onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.cart),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
            }
            AnimatedContent(
                modifier = Modifier
                    .alpha(alpha = countAnim)
                    .offset(3.dp, 3.dp)
                    .size(20.dp)
                    .background(MaterialTheme.colorScheme.onPrimary, shape = CircleShape)
                    .align(Alignment.BottomEnd), targetState = count.value
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        count.value.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}