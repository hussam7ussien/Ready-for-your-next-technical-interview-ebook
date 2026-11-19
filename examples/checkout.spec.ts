import { test, expect } from '@playwright/test';

test('customer places a cash order', async ({ page }) => {
  await page.goto('/menu');
  await page.getByRole('button', { name: 'Add item', exact: true }).click();
  await page.getByRole('link', { name: 'Cart', exact: true }).click();
  await expect(page.getByTestId('order-total')).toHaveText('150.00');
  await page.getByRole('radio', { name: 'Cash', exact: true }).check();
  const created = page.waitForResponse(r =>
    r.request().method() === 'POST' &&
    new URL(r.url()).pathname === '/api/orders'
  );
  await page.getByRole('button', { name: 'Place order', exact: true }).click();
  expect((await created).status()).toBe(201);
  await expect(page.getByRole('heading', { name: 'Order confirmed' }))
    .toBeVisible();
});
