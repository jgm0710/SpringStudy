
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
    <!-- Core Scripts - Include with every page -->
    
    <!-- <script src="js/jquery-1.10.2.js"></script> -->
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/plugins/metisMenu/jquery.metisMenu.js"></script>

    <!-- Page-Level Plugin Scripts - Tables -->
    <script src="/resources/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="/resources/js/plugins/dataTables/dataTables.bootstrap.js"></script>

    <!-- SB Admin Scripts - Include with every page -->
    <script src="/resources/js/sb-admin.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <!-- 새로고침시 메뉴바 펼쳐지는 문제 수정 -->
    <script>	
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
        			responsive: true
        		});
        $(".sidebar-nav")
        .attr("class","sidebar-nav navbar-collapse collapse")
        .attr("aria-expanded",'flase')
        .attr("style","height:1px");
    });
    </script>
   
    
    <!-- <script>
    $(document).ready(function() {
        $('#dataTables-example').dataTable();
    });
    </script> -->

</body>

</html>
    