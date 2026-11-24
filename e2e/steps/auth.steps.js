const { Given, When, Then, After } = require('@cucumber/cucumber');
const { chromium } = require('@playwright/test');
const assert = require('assert');

let browser;
let context;
let page;

function baseUrl(world) {
  return (world && world.parameters && world.parameters.baseUrl) || 'http://localhost:8080';
}

Given('the app is running', async function () {
  // We assume the user started the Spring Boot app separately.
});

When('I open the login page', async function () {
  browser = await chromium.launch();
  context = await browser.newContext();
  page = await context.newPage();

  await page.goto(`${baseUrl(this)}/login`);
});

When('I log in with username {string} and password {string}', async function (username, password) {
  await page.fill('input#username', username);
  await page.fill('input#password', password);
  await page.click('button[type="submit"]');
});

When('I open the welcome page', async function () {
  if (!page) {
    browser = await chromium.launch();
    context = await browser.newContext();
    page = await context.newPage();
  }
  await page.goto(`${baseUrl(this)}/welcome`);
});

When('I log out', async function () {
  // logout is a POST form with a submit button
  await page.click('button.logout-link');
});

Then('I should see the welcome page', async function () {
  await page.waitForURL(/\/welcome/);
  const title = await page.textContent('h1');
  assert.ok(title && title.includes('Welcome'), 'Expected welcome title');
});

Then('I should see the login page', async function () {
  await page.waitForURL(/\/login/);
  const heading = await page.textContent('h1');
  assert.strictEqual((heading || '').trim(), 'Login');
});

Then('I should see {string} on the page', async function (text) {
  const content = await page.content();
  assert.ok(content.includes(text), `Expected page to include: ${text}`);
});

Then('I should be redirected to the login page', async function () {
  await page.waitForURL(/\/login/);
});

After(async function () {
  if (browser) {
    await browser.close();
  }
  browser = undefined;
  context = undefined;
  page = undefined;
});
