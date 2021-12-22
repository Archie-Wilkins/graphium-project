########################
#   Imports
########################
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time



########################
# Variables
########################
driver = webdriver.Chrome('./chromedriver')
driver.fullscreen_window()



########################
# General Navigation Functions
########################
# Get pass Google's 'connection not provate' page (cuz Https)
def byPassConnectionNotPrivate():
    driver.get("https://localhost:8443/")

    advanceSafetyOptions = driver.find_element(By.XPATH,'//*[@id="details-button"]');
    advanceSafetyOptions.click()

    proceedButton = driver.find_element(By.XPATH,'//*[@id="proceed-link"]');
    proceedButton.click()


# Enter Our page
def startUp():
    driver.get("https://localhost:8443/")
    assert "Graphium" in driver.title
    driver.fullscreen_window()



########################
# Researcher Navigation Functions
########################
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

def researcherGotToShareDocumentPageViaOwnedDocument():
    firstDocumentShareBtn = driver.find_element(By.XPATH, "//a[contains(@href, '/document/share/1')]")
    firstDocumentShareBtn.click()
    assert "https://localhost:8443/document/share/1" in driver.current_url



########################
# Share Document Page Navigation Functions
########################
def shareSelectedOrganisation():
    beforeOrgsCount = len(driver.find_elements(By.XPATH, "//*[@id='list-orgs']/div"))
    submit = driver.find_element(By.XPATH, "//*[@id='submit-org']")
    submit.click()
    afterOrgsCount = len(driver.find_elements(By.XPATH, "//*[@id='list-orgs']/div"))
    assert beforeOrgsCount+1 == afterOrgsCount

def shareSelectedUser():
    beforeOrgsCount = len(driver.find_elements(By.XPATH, "//*[@id='list-users']/div"))
    submit = driver.find_element(By.XPATH, "//*[@id='submit-user']")
    submit.click()
    afterOrgsCount = len(driver.find_elements(By.XPATH, "//*[@id='list-users']/div"))
    assert beforeOrgsCount+1 == afterOrgsCount


########################
# Researcher Tests
#########################
def researcherCanShareDocument():
    print("Starting Test: Researcher Can Share Owned Document")
    try:
        byPassConnectionNotPrivate()
        print("Passed Stage 1 / 6")
        startUp()
        print("Passed Stage 2 / 6")
        logInAsResearcher()
        print("Passed Stage 3 / 6")
        researcherGotToShareDocumentPageViaOwnedDocument()
        print("Passed Stage 4 / 6")
        shareSelectedOrganisation()
        print("Passed Stage 5 / 6")
        shareSelectedUser()
        print("Passed Stage 6 / 6")
        print("Test Passed")
    except:
        print("Error - Test Failed")



########################
# Calling Tests
########################
researcherCanShareDocument()



########################
# End
########################
driver.close()
