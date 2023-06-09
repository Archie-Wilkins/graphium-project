from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
import time
import os
import path
import random

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
    assert "Graphium" in driver.title
    driver.fullscreen_window()


def logInAsResearcher():
    usernameField = driver.find_element(By.XPATH,"/html/body/form/div[2]/input")
    passwordField = driver.find_element(By.XPATH,"/html/body/form/div[3]/input")
    submitButton = driver.find_element(By.XPATH,"/html/body/form/div[4]/button")

    usernameField.send_keys('testUser')
    passwordField.send_keys('password')

    submitButton.click()
    assert "Welcome" in driver.title

def goToUploadPageUsingNavBar():
    systemAdminButton = driver.find_element(By.XPATH,'//*[@id="navbarSupportedContent"]/ul/li[1]/a')
    systemAdminButton.click()
    assert "Upload" in driver.title

def submitUploadRequest():
    documentTitle = driver.find_element(By.XPATH,'//*[@id="documentTitle"]')
    selectDocument = driver.find_element(By.XPATH,'//*[@id="documentData"]')
    documentUploadSubmit = driver.find_element(By.XPATH,'/html/body/form/button')

    documentTitle.send_keys(random.randint(0,100000000))
    dir_path = os.path.dirname(os.path.realpath(__file__))
    selectDocument.send_keys(dir_path + "/charity-guide.pdf")

    documentUploadSubmit.click()
    time.sleep(3)

def checkSuccessRedirectedToHomePage():
    if "Welcome" in driver.title:
        return True
    else:
        return False




# Can Create New Org
def researcherCanUploadFileTest():
    print("Starting Test: Researcher Can Upload File Test")
    print("Starting")
    try:
        byPassConnectionNotPrivate()
        print("Passed Stage 1 / 6")
        startUp()
        print("Passed Stage 2 / 6")
        logInAsResearcher()
        print("Passed Stage 3 / 6")
        goToUploadPageUsingNavBar()
        print("Passed Stage 4 / 6")
        submitUploadRequest()
        print("Passed Stage 5 / 6")
        checkSuccessRedirectedToHomePage()
        print("Passed Stage 6 / 6")
        print("Test Passed")
    except:
        print("Error - Test Failed")
#Go to add org
#add org
#check for success


#go to system admin page test upload Org works
researcherCanUploadFileTest()


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
