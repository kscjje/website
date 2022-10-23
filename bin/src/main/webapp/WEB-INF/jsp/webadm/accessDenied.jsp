<%
 /**
  * @Class Name : accessDenied.jsp
  * @Description : 권한 없음 노출 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.15    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.15
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>Error Page</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">

            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<section class="content">
      <div class="error-page">
        <h2 class="headline text-warning">!</h2>

        <div class="error-content">
          <h3><i class="fas fa-exclamation-triangle text-warning"></i> 사용권한이 없습니다.</h3>

          <p>
            관리자에게 문의해 주시기 바랍니다.
          </p>

        </div>
        <!-- /.error-content -->
      </div>
      <!-- /.error-page -->
    </section>