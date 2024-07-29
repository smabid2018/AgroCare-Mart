<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <style>
    html, body {
    margin: 0;
    padding: 0;
	}
	.footer{
    background-color: #3761af;
    color: white;
	}
    li{
        list-style: none;
    }
    
    
    a{
        text-decoration: none;
        color:white;
    }
    
    .upper-footer{
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
    
    
        .mid-footer{
            max-width: 500px;
            padding: 0 1rem;
    
           
        }
    
        .quick-link{
            margin-left: 0rem;
            text-align: center;
        }
    }
    
    
    .upper-footer{
        h6{
            font-size: large;
            font-weight: bold;
            text-align: center;
        }
    }
    
    .lower-footer{
        display: flex;
        justify-content: center;
    }</style>
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0"
    />
  </head>
  <body>
    <footer class="footer">
      <div class="upper-footer">
        <div class="right-footer">
          <h6>Quick Links</h6>
          <div class="quick-link">
            <ul class="links-list">
              <li>
                <a href="http://localhost:8081/AgroCareMart/pages/adminRegistration.jsp">Become a Seller</a>
              </li>
              <li>
                <a href="#">Search Products</a>
              </li>
              <li>
                <a href="#">Register</a>
              </li>
              <li>
                <a href="#">Login</a>
              </li>
            </ul>
          </div>
        </div>

        <div class="mid-footer">
          <h6>About</h6>
          <p class="text-justify" style="font-size: small;">
            AgroCare Mart is a digital marketplace tailored for farmers,
            offering a wide range of agricultural products and support services.
            With a user-friendly interface, verified sellers, and a robust
            support system, AgroCare Mart simplifies the procurement process and
            enhances the farming experience. By providing access to quality
            products, expert advice, and reliable transactions, AgroCare Mart
            aims to empower farmers and foster growth in the agricultural
            sector.
          </p>
          <br><br>
        </div>

        <div class="left-footer">
          <h6>Address</h6>

          <div class="address-links">
          <ul>
            <li>
              <span class="material-symbols-outlined">location_on</span>
              Dillibazar-29 Kathmandu
            </li>
            <li>
              <a href="#">
                <span class="material-symbols-outlined"> call </span>
                +977 9812240766</a
              >
            </li>
            <li>
              <a href="#">
                <span class="material-symbols-outlined"> mail </span>
                support@agrocaremart.com.np</a>
            </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="lower-footer">
        <p class="copyright-text">
          Copyright &copy; 2017 All Rights Reserved by
          <a href="#">AgroCare Mart</a>
        </p>
      </div>
    </footer>
  </body>
</html>
