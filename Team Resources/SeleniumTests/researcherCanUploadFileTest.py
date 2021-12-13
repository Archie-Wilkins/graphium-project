from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
import time
import os
import pyautogui
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
    print(driver.title)
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
    print(driver.title)

def goToUploadPageUsingNavBar():
    systemAdminButton = driver.find_element(By.XPATH,'//*[@id="navbarSupportedContent"]/ul/li[1]/a')
    systemAdminButton.click()
    assert "Upload" in driver.title
    print(driver.title)

def submitUploadRequest():
    documentTitle = driver.find_element(By.XPATH,'//*[@id="documentTitle"]')
    selectDocument = driver.find_element(By.XPATH,'//*[@id="documentData"]')
    documentUploadSubmit = driver.find_element(By.XPATH,'/html/body/form/button')
    # selectDocument = EC.presence_of_element_located((By.XPATH, '//*[@id="documentData"]'))  # Example xpath


    documentTitle.send_keys(random.randint(0,100000000))
    # WebDriverWait(self.driver, 10).until(selectDocument).click() # This opens the windows file selector
    dir_path = os.path.dirname(os.path.realpath(__file__))
    print(dir_path)

    selectDocument.send_keys(dir_path + "/charity-guide.pdf")
    # pyautogui.write("C:/Users/c2043958/OneDrive - Cardiff University/Documents/Year 2/Assessments/Semester 1/ClientProject/Team Resources/SeleniumTests/charity-guide.pdf")
    # pyautogui.press('enter')
    print("Upload begin")
    # selectDocument.sendKeys(os.path.abspath(os.getcwd()) +"/charity-guide.pdf")
    time.sleep(1)
    print("Test")
    documentUploadSubmit.click()
    time.sleep(1)
    print("Test")
    print("Submitted")
    time.sleep(3)

def checkSuccessRedirectedToHomePage():
    print(driver.title)
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
