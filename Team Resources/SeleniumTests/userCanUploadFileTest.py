from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By

driver = webdriver.Chrome('./chromedriver')
driver.fullscreen_window()

def byPassConnectionNotPrivate():
    driver.get("https://localhost:8443/")

    advanceSafetyOptions = driver.find_element(By.XPATH,'//*[@id="details-button"]');
    advanceSafetyOptions.click()

    proceedButton = driver.find_element(By.XPATH,'//*[@id="proceed-link"]');
    proceedButton.click()



def startUp():
    driver.get("https://localhost:8443/")
    print(driver.title)
    assert "Graphium" in driver.title
    driver.fullscreen_window()


def logInAsSystemAdmin():
    usernameField = driver.find_element(By.XPATH,"/html/body/form/div[2]/input");
    passwordField = driver.find_element(By.XPATH,"/html/body/form/div[3]/input");
    submitButton = driver.find_element(By.XPATH,"/html/body/form/div[4]/button");

    usernameField.send_keys('testSystemAdmin')
    passwordField.send_keys('password')

    submitButton.click()

    assert "Welcome" in driver.title
    print(driver.title)

def goToSystemAdminPageUsingNavBar():
    systemAdminButton = driver.find_element(By.XPATH,"//*[@id="navbarSupportedContent"]/ul/li[4]/a");
    systemAdminButton.click()
    assert "System Admin" in driver.title
    print(driver.title)

def goToSystemAdminPageUsingNavBar():
    systemAdminNewOrgButton = driver.find_element(By.XPATH,"//*[@id="navbarSupportedContent"]/ul/li[4]/a");
    systemAdminNewOrgButton.click()
    assert "New Organisation" in driver.title
    print(driver.title)

def checkSystemAdminFiles():
    print("Complete this")

def createNewOrganisation():
    print("Complete this")


# Can Create New Org
byPassConnectionNotPrivate()
startUp()
logInAsSystemAdmin()
goToSystemAdminPageUsingNavBar()
#Go to add org
#add org
#check for success


#go to system admin page test upload Org works



#can view all sys admin files

# test for researcher viewing system admin page being denieds

# test for orgAdmin viewing org admin files




# usernameField.send_keys(Keys.RETURN);
#
# passwordField = driver.find_element_by_xpath("/html/body/form/div[3]/input");
# usernameField.send_keys('password')
# passwordField.sendKeys(Keys.RETURN);

# elem = driver.find_element("q")
# elem.clear()
# elem.send_keys("pycon")
# elem.send_keys(Keys.RETURN)
# assert "No results found." not in driver.page_source

driver.close()
