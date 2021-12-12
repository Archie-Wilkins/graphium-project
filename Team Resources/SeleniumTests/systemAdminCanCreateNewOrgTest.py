from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time


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
    usernameField = driver.find_element(By.XPATH,"/html/body/form/div[2]/input")
    passwordField = driver.find_element(By.XPATH,"/html/body/form/div[3]/input")
    submitButton = driver.find_element(By.XPATH,"/html/body/form/div[4]/button")

    usernameField.send_keys('testSystemAdmin')
    passwordField.send_keys('password')

    submitButton.click()

    assert "Welcome" in driver.title
    print(driver.title)

def goToSystemAdminPageUsingNavBar():
    systemAdminButton = driver.find_element(By.XPATH,'//*[@id="navbarSupportedContent"]/ul/li[4]/a')
    systemAdminButton.click()
    assert "System Admin" in driver.title
    print(driver.title)

def goToSystemAdminRegisterNewAdminPageUsingNavBar():
    systemAdminNewOrgButton = driver.find_element(By.XPATH,'//*[@id="navbarSupportedContent"]/ul/li[6]/a')
    systemAdminNewOrgButton.click()
    assert "New Organisation" in driver.title
    print(driver.title)



def createNewOrganisation():
    newOrgNameInput = driver.find_element(By.XPATH,'//*[@id="organisationNameInput"]')
    newOrgEmailInput = driver.find_element(By.XPATH,'//*[@id="emailInput"]')
    newOrgSubmitButton = driver.find_element(By.XPATH,'/html/body/div/div/form/div/div[2]/button')

    newOrgNameInput.send_keys('The Selenium Test Company')
    newOrgEmailInput.send_keys('Selenium@SeleniumTest.com')
    newOrgSubmitButton.click()
    print("Register New Organisation Form Submitted")
    time.sleep(3)

def checkForSuccessMessage():
    print("Nice")
    responseMessage = driver.find_element(By.XPATH,'/html/body/div/div/div')
    print("Nice 2")
    if "already exists" in responseMessage.text:
        print("Already Exists")
    elif "has been saved" in responseMessage.text:
        print("Success")
    else:
        print("FAIL")


def checkSystemAdminFiles():
    print("Complete this")

# Can Create New Org
def systemAdminCanCreateNewOrganisationAdmin():
    print("Starting Test: System Admin Can Create New Organisation Admin")
    print("Starting")
    try:
        byPassConnectionNotPrivate()
        print("Passed Stage 1 / 6")
        startUp()
        print("Passed Stage 2 / 6")
        logInAsSystemAdmin()
        print("Passed Stage 3 / 6")
        goToSystemAdminRegisterNewAdminPageUsingNavBar()
        print("Passed Stage 4 / 6")
        createNewOrganisation()
        print("Passed Stage 5 / 6")
        checkForSuccessMessage()
        print("Passed Stage 6 / 6")
        print("Test Passed")
    except:
        print("Error - Test Failed")
#Go to add org
#add org
#check for success
systemAdminCanCreateNewOrganisationAdmin()


driver.close()
