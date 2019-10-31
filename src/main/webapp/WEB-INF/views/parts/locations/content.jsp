      <%@ page pageEncoding="UTF-8" %>
      <section class="portfolio-flyer py-5" id="gallery">
        <div class="container pt-lg-3 pb-md-5">
          <a name="list"></a>
          <h3 class="tittle  text-center my-lg-5 my-3">Previously visited locations</h3>
            <div class="row pb-lg-5 mt-3 mt-lg-5">
              <div class="card-columns">
                <c:set var="country" value="" />
                <c:forEach items="${locations}" var="location">
                  <c:if test="${country != location.countryName}">
                          <c:if test="${!empty country}"></ul>
                        </div>
                    </div></c:if>
                    <c:set var="country" value="${location.countryName}" />
                    <div class="card">
                      <div class="card-body detail">
                        <h4 class="card-title">${location.countryName}</h4>
                          <ul>
                  </c:if>
                            <li><a href="${root}${location.path}"><span class="fa fa-globe"></span> ${location.cityName}</a>
                </c:forEach>
                          <c:if test="${!empty country}"></ul>
                        </div>
                    </div></c:if>
                </div>
            </div>
        </div>
      </section>
