//{
//private ViewPager mViewPager;
//ImageView accout_ic;
//private RecyclerView recyclerView;
//private List_phimmoi_Adapter adapter;
//private DatabaseHelper databaseHelper;
//
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_trang_chu);
//    recyclerView = findViewById(R.id.recyclerView); // Đảm bảo bạn đã thêm RecyclerView vào layout
//    recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//    databaseHelper = new DatabaseHelper(this);
//    if (databaseHelper.getAllPhim().isEmpty()) {
//        databaseHelper.addPhim(new PhimMoi("Phim Avengers", R.drawable.img));
//        databaseHelper.addPhim(new PhimMoi("Phim Spider Man", R.drawable.mat_biec));
//        // Thêm các phim khác nếu cần
//    }
//
//    // Khởi tạo RecyclerView
//    recyclerView = findViewById(R.id.recyclerView);
//    recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//    // Lấy danh sách phim từ cơ sở dữ liệu
//    List<PhimMoi> phimList = databaseHelper.getAllPhim();
//    adapter = new List_phimmoi_Adapter(phimList);
//    recyclerView.setAdapter(adapter);
//    List<PhimMoi> phimList = databaseHelper.getAllPhim(); // Lấy danh sách phim từ cơ sở dữ liệu
//
//    adapter = new List_phimmoi_Adapter(phimList);
//    recyclerView.setAdapter(adapter);
//
//
//
//    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//        return insets;
//    });
//
//
//    int[] images = {
//            R.drawable.iteam1, R.drawable.iteam2, R.drawable.iteam3, R.drawable.iteam4, R.drawable.iteam5
//    };
//
//
//    mViewPager = findViewById(R.id.viewPagerMain);
//    viewPagerAdapter mViewPagerAdapter = new viewPagerAdapter(this, images);
//    mViewPager.setAdapter(mViewPagerAdapter);
//    accout_ic = findViewById(R.id.accout_ic);
//    accout_ic.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(TrangChu.this, Manhinhadmin.class);
//            startActivity(intent);
//        }
//    });
//}
//}